package com.example.jdstreetwear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jdstreetwear.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
