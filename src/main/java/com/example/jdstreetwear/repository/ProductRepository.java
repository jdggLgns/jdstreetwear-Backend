package com.example.jdstreetwear.repository;

import com.example.jdstreetwear.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    @EntityGraph(attributePaths = {"category", "discount", "supplier", "tshirt", "sweatshirt", "inventories"})
    List<Product> findAll();

    @EntityGraph(attributePaths = {"category", "discount", "supplier", "tshirt", "sweatshirt", "inventories"})
    Optional<Product> findById(Long id);

    @EntityGraph(attributePaths = {"category", "discount", "supplier", "tshirt", "sweatshirt", "inventories"})
    List<Product> findByName(String name);

    @EntityGraph(attributePaths = {"category", "discount", "supplier", "tshirt", "sweatshirt", "inventories"})
    List<Product> findByCategoryId(Long categoryId);

    @EntityGraph(attributePaths = {"category", "discount", "supplier", "tshirt", "sweatshirt", "inventories"})
    List<Product> findByNameAndCategoryId(String name, Long categoryId);

}
