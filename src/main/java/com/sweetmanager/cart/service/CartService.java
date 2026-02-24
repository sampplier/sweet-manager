package com.sweetmanager.cart.service;

import com.sweetmanager.cart.domain.Cart;
import com.sweetmanager.cart.domain.CartItem;
import com.sweetmanager.cart.dto.CartItemDTO;
import com.sweetmanager.cart.dto.CartItemRequestDTO;
import com.sweetmanager.cart.dto.CartResponseDTO;
import com.sweetmanager.cart.repository.CartRepository;
import com.sweetmanager.order.dto.OrderRequestDTO;
import com.sweetmanager.order.service.OrderService;
import com.sweetmanager.product.domain.Product;
import com.sweetmanager.product.repository.ProductRepository;
import com.sweetmanager.user.domain.User;
import com.sweetmanager.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final OrderService orderService;

    @Transactional
    public Cart getCartForUser(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return cartRepo.findByUser(user)
                .orElseGet(() -> cartRepo.save(Cart.builder().user(user).items(new ArrayList<>()).build()));
    }

    @Transactional
    public CartResponseDTO getCartForUserDTO(String email) {
        Cart cart = getCartForUser(email);
        cart.getItems().size(); // inicializa lista para evitar LazyInitializationException
        return mapToDTO(cart);
    }

    @Transactional
    public CartResponseDTO addItemForUser(String email, CartItemRequestDTO req) {
        if (req.getQuantity() <= 0) throw new RuntimeException("Quantidade inválida");

        Cart cart = getCartForUser(email);
        Product product = productRepo.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (!product.getAvailable() || product.getStock() <= 0) {
            throw new RuntimeException("Produto indisponível: " + product.getName());
        }

        int quantityToAdd = Math.min(req.getQuantity(), product.getStock());

        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            CartItem item = existingItemOpt.get();
            int newQuantity = Math.min(item.getQuantity() + quantityToAdd, product.getStock());
            item.setQuantity(newQuantity);
        } else {
            CartItem item = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantityToAdd)
                    .build();
            cart.getItems().add(item);
        }

        cartRepo.save(cart);
        return mapToDTO(cart);
    }

    @Transactional
    public CartResponseDTO removeItemForUser(String email, Long productId) {
        Cart cart = getCartForUser(email);
        cart.getItems().removeIf(i -> i.getProduct().getId().equals(productId));
        cartRepo.save(cart);
        return mapToDTO(cart);
    }

    @Transactional
    public void checkout(String email) {
        Cart cart = getCartForUser(email);
        if (cart.getItems().isEmpty()) throw new RuntimeException("Carrinho vazio.");

        for (CartItem item : cart.getItems()) {
            Product product = productRepo.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            if (!product.getAvailable() || product.getStock() < item.getQuantity())
                throw new RuntimeException("Estoque insuficiente para: " + product.getName());

            // Cria pedido PENDENTE, sem tocar no estoque
            OrderRequestDTO orderReq = new OrderRequestDTO();
            orderReq.setProductId(product.getId());
            orderReq.setQuantity(item.getQuantity());
            orderReq.setCustomerName(email);
            orderService.create(orderReq);
        }

        // Limpa carrinho
        cart.getItems().clear();
        cartRepo.save(cart);
    }



    private CartResponseDTO mapToDTO(Cart cart) {
        CartResponseDTO dto = new CartResponseDTO();
        dto.setCartId(cart.getId());
        dto.setItems(cart.getItems().stream().map(i -> {
            CartItemDTO ci = new CartItemDTO();
            ci.setProductId(i.getProduct().getId());
            ci.setProductName(i.getProduct().getName());
            ci.setPrice(i.getProduct().getPrice());
            ci.setQuantity(i.getQuantity());
            return ci;
        }).collect(Collectors.toList()));
        return dto;
    }
}
