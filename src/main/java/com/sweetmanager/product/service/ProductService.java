package com.sweetmanager.product.service;

import com.sweetmanager.exception.product.ProductInUseException;
import com.sweetmanager.product.domain.Product;
import com.sweetmanager.order.repository.OrderRepository;
import com.sweetmanager.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repo;
    private final OrderRepository orderRepo;

    public List<Product> findAll() {
        return repo.findAll();
    }

    public Product save(Product p) {
        return repo.save(p);
    }

    public void delete(Long id) {
        boolean hasOrders = orderRepo.existsByProductId(id);
        if (hasOrders) {
            throw new ProductInUseException("Não é possível excluir: este produto possui pedidos vinculados.");
        }
        repo.deleteById(id);
    }

    @Transactional
    public Product addStock(Long productId, int quantity) {
        if (quantity <= 0) throw new RuntimeException("Quantidade inválida");
        Product product = repo.findById(productId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        int newStock = (product.getStock() == null ? 0 : product.getStock()) + quantity;
        product.setStock(newStock);
        if (newStock > 0) product.setAvailable(true);
        return repo.save(product);
    }
}

