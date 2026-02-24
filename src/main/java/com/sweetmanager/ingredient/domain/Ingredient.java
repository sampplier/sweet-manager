package com.sweetmanager.ingredient.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer stock; // total disponível
    private String unit;     // ex: "g", "ml", "un"
    // Indica se o produto ainda deve aparecer no catálogo
    @Builder.Default
    private Boolean available = true;
}
