package com.example.jdstreetwear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jdstreetwear.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
