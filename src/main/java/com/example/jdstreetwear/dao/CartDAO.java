package com.example.jdstreetwear.dao;

import com.example.jdstreetwear.model.Cart;
import com.example.jdstreetwear.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CartDAO {

    @Autowired
    private CartRepository cartRepository;

    public Optional<Cart> findByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

}
