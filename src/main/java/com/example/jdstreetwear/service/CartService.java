package com.example.jdstreetwear.service;

import com.example.jdstreetwear.dao.CartDAO;
import com.example.jdstreetwear.dao.CartItemDAO;
import com.example.jdstreetwear.model.*;
import com.example.jdstreetwear.repository.CustomerRepository;
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
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    public Cart addToCart(Long customerId, Long productId, int quantity) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        User user = customer.getUser();
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartDAO.findByUserId(user.getId()).orElseGet(() -> new Cart(user));
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

    public Cart getCart(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        User user = customer.getUser();
        return cartDAO.findByUserId(user.getId()).orElseGet(() -> {
            Cart newCart = new Cart(user);
            return cartDAO.save(newCart);
        });
    }

    public void clearCart(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        User user = customer.getUser();
        Cart cart = cartDAO.findByUserId(user.getId()).orElseThrow(() -> new RuntimeException("Cart not found"));
        for (CartItem item : cart.getItems()) {
            cartItemDAO.delete(item);
        }
        cart.getItems().clear();
        cartDAO.save(cart);
    }

}
