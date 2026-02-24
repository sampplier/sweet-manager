package com.sweetmanager.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DailySalesDTO {
    private LocalDate date;
    private Long salesCount;
    private Double totalValue;
}
