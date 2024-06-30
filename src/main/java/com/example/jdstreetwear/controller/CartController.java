package com.example.jdstreetwear.controller;

import com.example.jdstreetwear.model.Cart;
import com.example.jdstreetwear.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public Cart addToCart(@RequestParam Long customerId, @RequestParam Long productId, @RequestParam int quantity) {
        return cartService.addToCart(customerId, productId, quantity);
    }

    @DeleteMapping("/remove")
    public Cart removeFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        return cartService.removeFromCart(userId, productId);
    }

    @GetMapping
    public Cart getCart(@RequestParam Long customerId) {
        return cartService.getCart(customerId);
    }
    @DeleteMapping("/clear")
    public void clearCart(@RequestParam Long customerId) {
        cartService.clearCart(customerId);
    }
}
