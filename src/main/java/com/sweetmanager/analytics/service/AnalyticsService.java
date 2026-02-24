package com.sweetmanager.analytics.service;

import com.sweetmanager.analytics.dto.DailySalesDTO;
import com.sweetmanager.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final OrderRepository orderRepo;

    public List<DailySalesDTO> getDailySales() {
        List<Object[]> raw = orderRepo.getDailySales();
        List<DailySalesDTO> result = new ArrayList<>();

        for (Object[] row : raw) {

            LocalDate date;

            // PostgreSQL retorna java.sql.Date
            if (row[0] instanceof Date sqlDate) {
                date = sqlDate.toLocalDate();
            } else {
                date = (LocalDate) row[0];
            }

            Long salesCount = ((Number) row[1]).longValue();
            Double totalValue = row[2] != null ? ((Number) row[2]).doubleValue() : 0.0;

            result.add(new DailySalesDTO(date, salesCount, totalValue));
        }

        return result;
    }
}
