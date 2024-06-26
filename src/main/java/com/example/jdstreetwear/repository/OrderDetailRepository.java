package com.example.jdstreetwear.repository;

import com.example.jdstreetwear.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<Order, Long> {
}
