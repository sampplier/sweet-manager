package com.sweetmanager.cart.controller;

import com.sweetmanager.cart.dto.CartItemRequestDTO;
import com.sweetmanager.cart.dto.CartResponseDTO;
import com.sweetmanager.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@EnableMethodSecurity
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {

    private final CartService cartService;

    private String getUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public CartResponseDTO getCart() {
        return cartService.getCartForUserDTO(getUserEmail());
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public CartResponseDTO addItem(@RequestBody CartItemRequestDTO req) {
        return cartService.addItemForUser(getUserEmail(), req);
    }

    @PostMapping("/remove/{productId}")
    @PreAuthorize("isAuthenticated()")
    public CartResponseDTO removeItem(@PathVariable Long productId) {
        return cartService.removeItemForUser(getUserEmail(), productId);
    }

    @PostMapping("/checkout")
    @PreAuthorize("isAuthenticated()")
    public String checkout() {
        cartService.checkout(getUserEmail());
        return "Checkout realizado com sucesso!";
    }
}
