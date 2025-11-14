package com.sweetmanager.dto;

import lombok.Data;

@Data
public class IngredientDTO {
    private Long id;
    private String name;
    private Double quantity;
    private String unit;
}
