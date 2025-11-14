package com.sweetmanager.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private Long id;
    private String customerName;
    private Integer quantity;
    private Double total;
    private LocalDateTime createdAt;
    private Long productId;
}
