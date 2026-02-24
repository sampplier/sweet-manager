package com.sweetmanager.cart.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartResponseDTO {
    private Long cartId;
    private List<CartItemDTO> items;
}
