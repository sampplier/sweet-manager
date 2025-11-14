package com.sweetmanager.controller;

import com.sweetmanager.model.Ingredient;
import com.sweetmanager.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
@CrossOrigin
public class IngredientController {
    private final IngredientService service;

    @GetMapping
    public List<Ingredient> all() { return service.findAll(); }

    @PostMapping
    public Ingredient create(@RequestBody Ingredient i) { return service.save(i); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }
}
