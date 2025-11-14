package com.sweetmanager.service;

import com.sweetmanager.model.Ingredient;
import com.sweetmanager.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository repo;

    public List<Ingredient> findAll() { return repo.findAll(); }
    public Ingredient save(Ingredient i) { return repo.save(i); }
    public void delete(Long id) { repo.deleteById(id); }
}
