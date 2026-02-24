package com.sweetmanager.order.service;

import com.sweetmanager.order.dto.OrderRequestDTO;
import com.sweetmanager.exception.order.InsufficientStockException;
import com.sweetmanager.exception.order.OutOfStockException;
import com.sweetmanager.order.domain.Order;
import com.sweetmanager.product.domain.Product;
import com.sweetmanager.order.enums.OrderStatus;
import com.sweetmanager.order.repository.OrderRepository;
import com.sweetmanager.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;

    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    public List<Order> findByUser(String email) {
        return orderRepo.findByCreatedBy(email);
    }

    @Transactional
    public Order create(OrderRequestDTO req) {
        Product product = productRepo.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (!product.getAvailable() || product.getStock() <= 0)
            throw new OutOfStockException("O produto '" + product.getName() + "' está esgotado.");

        if (req.getQuantity() <= 0)
            throw new RuntimeException("Quantidade inválida.");

        Order order = Order.builder()
                .customerName(req.getCustomerName())
                .quantity(req.getQuantity())
                .total(product.getPrice() * req.getQuantity())
                .product(product)
                .status(OrderStatus.EM_ANDAMENTO) //
                .createdBy(SecurityContextHolder.getContext().getAuthentication().getName())
                .build();

        return orderRepo.save(order);
    }

    // Subtração de estoque só ao marcar como CONCLUIDO
    @Transactional
    public Order updateStatus(Long id, OrderStatus newStatus) {
        Order order = orderRepo.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        if (order.getStatus() == newStatus) return order;

        if (newStatus == OrderStatus.CONCLUIDO) {
            Product p = productRepo.findById(order.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            int newStock = p.getStock() - order.getQuantity();
            if (newStock < 0) throw new RuntimeException("Estoque insuficiente para concluir pedido");

            p.setStock(newStock);
            p.setAvailable(newStock > 0);
            productRepo.save(p);
        }

        // cancelar pedido devolve estoque
        if (newStatus == OrderStatus.CANCELADO && order.getStatus() != OrderStatus.CANCELADO) {
            Product p = productRepo.findById(order.getProduct().getId()).orElse(null);
            if (p != null) {
                p.setStock(p.getStock() + order.getQuantity());
                p.setAvailable(true);
                productRepo.save(p);
            }
        }

        order.setStatus(newStatus);
        return orderRepo.save(order);
    }
}
