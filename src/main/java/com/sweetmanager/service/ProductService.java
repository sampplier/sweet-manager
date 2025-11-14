package com.sweetmanager.service;

import com.sweetmanager.model.Product;
import com.sweetmanager.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repo;

    public List<Product> findAll() { return repo.findAll(); }
    public Product save(Product p) { return repo.save(p); }
    public void delete(Long id) { repo.deleteById(id); }
}
