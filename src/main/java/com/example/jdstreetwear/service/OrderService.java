package com.example.jdstreetwear.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.jdstreetwear.dao.OrderDAO;
import com.example.jdstreetwear.model.Order;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;

    public List<Order> getAllOrders() {
        return orderDAO.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderDAO.findById(id);
    }

    public Order createOrder(Order order) {
        return orderDAO.save(order);
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Optional<Order> optionalOrder = orderDAO.findById(id);

        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setCustomer(orderDetails.getCustomer());
            order.setDeliveryAddress(orderDetails.getDeliveryAddress());
            order.setTotalPrice(orderDetails.getTotalPrice());
            order.setOrderDate(orderDetails.getOrderDate());
            order.setStatus(orderDetails.getStatus());
            order.setOrderDetails(orderDetails.getOrderDetails());
            return orderDAO.save(order);
        } else {
            throw new RuntimeException("Order not found with id " + id);
        }
    }

    public void deleteOrder(Long id) {
        orderDAO.deleteById(id);
    }
}
