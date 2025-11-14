package com.sweetmanager.service;

import com.sweetmanager.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final OrderRepository orderRepo;

    public Long countOrders() {
        return orderRepo.count();
    }
}
