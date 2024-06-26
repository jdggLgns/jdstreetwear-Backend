package com.example.jdstreetwear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jdstreetwear.model.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
