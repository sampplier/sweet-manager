package com.sweetmanager.analytics.controller;

import com.sweetmanager.analytics.dto.DailySalesDTO;
import com.sweetmanager.analytics.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@EnableMethodSecurity
@CrossOrigin
public class AnalyticsController {

    private final AnalyticsService service;

    @GetMapping("/daily-sales")
    @PreAuthorize("hasRole('ADMIN')")
    public List<DailySalesDTO> getDailySales() {
        return service.getDailySales();
    }
}
