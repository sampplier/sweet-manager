package com.sweetmanager.service;

import com.sweetmanager.model.Order;
import com.sweetmanager.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repo;

    public List<Order> findAll() { return repo.findAll(); }

    public Order save(Order order) {
        order.setCreatedAt(LocalDateTime.now());
        return repo.save(order);
    }
}
