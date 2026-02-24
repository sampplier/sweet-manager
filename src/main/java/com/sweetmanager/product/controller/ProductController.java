package com.sweetmanager.product.controller;

import com.sweetmanager.exception.product.ProductInUseException;
import com.sweetmanager.product.domain.Product;
import com.sweetmanager.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@EnableMethodSecurity
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    private final ProductService service;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Product> all() {
        return service.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Product create(@RequestBody Product p) {
        return service.save(p);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        } catch (ProductInUseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update-stock/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Product updateStock(@PathVariable Long id, @RequestParam int quantity) {
        return service.addStock(id, quantity);
    }
}
