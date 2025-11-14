package com.sweetmanager.controller;

import com.sweetmanager.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin
public class DashboardController {
    private final DashboardService service;

    @GetMapping("/orders/count")
    public Long countOrders() {
        return service.countOrders();
    }
}
