package com.sweetmanager.controller;

import com.sweetmanager.model.Order;
import com.sweetmanager.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin
public class OrderController {
    private final OrderService service;

    @GetMapping
    public List<Order> all() { return service.findAll(); }

    @PostMapping
    public Order create(@RequestBody Order o) { return service.save(o); }
}
