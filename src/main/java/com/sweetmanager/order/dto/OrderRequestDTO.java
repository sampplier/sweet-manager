package com.sweetmanager.order.dto;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private Long productId;
    private String customerName;
    private Integer quantity;
}
