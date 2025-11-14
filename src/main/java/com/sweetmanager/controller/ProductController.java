package com.sweetmanager.controller;

import com.sweetmanager.model.Product;
import com.sweetmanager.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")

public class ProductController {
    private final ProductService service;

    @GetMapping
    public List<Product> all() { return service.findAll(); }

    @PostMapping
    public Product create(@RequestBody Product p) { return service.save(p); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }
}
