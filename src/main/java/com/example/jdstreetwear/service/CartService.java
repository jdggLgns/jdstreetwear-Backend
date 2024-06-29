package com.example.jdstreetwear.service;

import com.example.jdstreetwear.dao.CartDAO;
import com.example.jdstreetwear.dao.CartItemDAO;
import com.example.jdstreetwear.model.Cart;
import com.example.jdstreetwear.model.CartItem;
import com.example.jdstreetwear.model.Product;
import com.example.jdstreetwear.model.User;
import com.example.jdstreetwear.repository.ProductRepository;
import com.example.jdstreetwear.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private CartItemDAO cartItemDAO;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public Cart addToCart(Long userId, Long productId, int quantity) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartDAO.findByUserId(userId).orElseGet(() -> new Cart(user));
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem(cart, product, 0));

        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItemDAO.save(cartItem);
        cart.getItems().add(cartItem);

        return cartDAO.save(cart);
    }

    public Cart removeFromCart(Long userId, Long productId) {
        Cart cart = cartDAO.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        cart.getItems().remove(cartItem);
        cartItemDAO.delete(cartItem);

        return cartDAO.save(cart);
    }

    public Cart getCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return cartDAO.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart(user);
            return cartDAO.save(newCart);
        });
    }
}
