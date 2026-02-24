package com.sweetmanager.ingredient.dto;

import lombok.Data;

@Data
public class IngredientDTO {
    private Long id;
    private String name;
    private Integer stock;
    private String unit;
    private Boolean available;
}
