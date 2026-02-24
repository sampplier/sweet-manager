package com.sweetmanager.cart.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
}
