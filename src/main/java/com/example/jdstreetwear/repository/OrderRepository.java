package com.example.jdstreetwear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jdstreetwear.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
