package com.sweetmanager.ingredient.controller;

import com.sweetmanager.ingredient.dto.IngredientDTO;
import com.sweetmanager.ingredient.domain.Ingredient;
import com.sweetmanager.ingredient.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
@EnableMethodSecurity
@CrossOrigin(origins = "http://localhost:5173")
public class IngredientController {

    private final IngredientService service;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<IngredientDTO> all() {
        return service.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public IngredientDTO create(@RequestBody Ingredient ingredient) {
        return service.save(ingredient);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PatchMapping("/add-stock/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public IngredientDTO addStock(@PathVariable Long id, @RequestParam int quantity) {
        return service.addStock(id, quantity);
    }

    @PatchMapping("/remove-stock/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public IngredientDTO removeStock(@PathVariable Long id, @RequestParam int quantity) {
        return service.removeStock(id, quantity);
    }
}
