package com.sweetmanager.product.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    private Integer stock;

    // Indica se o produto ainda deve aparecer no catálogo
    @Builder.Default
    private Boolean available = true;
}
