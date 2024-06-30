package com.example.jdstreetwear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jdstreetwear.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByUserId(Long userId);

}
