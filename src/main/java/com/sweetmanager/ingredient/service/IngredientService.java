package com.sweetmanager.ingredient.service;

import com.sweetmanager.ingredient.domain.Ingredient;
import com.sweetmanager.ingredient.dto.IngredientDTO;
import com.sweetmanager.ingredient.repository.IngredientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository repo;

    public List<IngredientDTO> findAll() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public IngredientDTO save(Ingredient ingredient) {
        if (ingredient.getStock() == null) ingredient.setStock(0);
        ingredient.setAvailable(ingredient.getStock() > 0);
        Ingredient saved = repo.save(ingredient);
        return toDTO(saved);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Transactional
    public IngredientDTO addStock(Long ingredientId, int quantity) {
        if (quantity <= 0) throw new RuntimeException("Quantidade inválida");

        Ingredient ingredient = repo.findById(ingredientId)
                .orElseThrow(() -> new RuntimeException("Ingrediente não encontrado"));

        int newStock = (ingredient.getStock() == null ? 0 : ingredient.getStock()) + quantity;
        ingredient.setStock(newStock);
        ingredient.setAvailable(newStock > 0);

        return toDTO(repo.save(ingredient));
    }

    @Transactional
    public IngredientDTO removeStock(Long ingredientId, int quantity) {
        if (quantity <= 0) throw new RuntimeException("Quantidade inválida");

        Ingredient ingredient = repo.findById(ingredientId)
                .orElseThrow(() -> new RuntimeException("Ingrediente não encontrado"));

        int newStock = Math.max(0, (ingredient.getStock() == null ? 0 : ingredient.getStock()) - quantity);
        ingredient.setStock(newStock);
        ingredient.setAvailable(newStock > 0);

        return toDTO(repo.save(ingredient));
    }

    private IngredientDTO toDTO(Ingredient ingredient) {
        IngredientDTO dto = new IngredientDTO();
        dto.setId(ingredient.getId());
        dto.setName(ingredient.getName());
        dto.setStock(ingredient.getStock());
        dto.setUnit(ingredient.getUnit());
        dto.setAvailable(ingredient.getAvailable());
        return dto;
    }
}
