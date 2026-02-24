package com.sweetmanager.order.controller;

import com.sweetmanager.order.dto.OrderRequestDTO;
import com.sweetmanager.exception.order.InsufficientStockException;
import com.sweetmanager.exception.order.OutOfStockException;
import com.sweetmanager.order.domain.Order;
import com.sweetmanager.order.enums.OrderStatus;
import com.sweetmanager.order.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin
@EnableMethodSecurity
public class OrderController {

    private final OrderService service;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Order> all() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin)
            return service.findAll();

        return service.findByUser(email);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Order create(@RequestBody OrderRequestDTO req) {
        return service.create(req);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public Order updateStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status
    ) {
        return service.updateStatus(id, status);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<String> handleInsufficient(InsufficientStockException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<String> handleOutOfStock(OutOfStockException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
