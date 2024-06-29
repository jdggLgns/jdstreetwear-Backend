package com.example.jdstreetwear.dao;

import com.example.jdstreetwear.model.Product;
import com.example.jdstreetwear.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductDAO {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }


    public List<Product> searchProducts(String name, Long categoryId) {
        if (name != null && categoryId != null) {
            return productRepository.findByNameAndCategoryId(name, categoryId);
        } else if (name != null) {
            return productRepository.findByName(name);
        } else if (categoryId != null) {
            return productRepository.findByCategoryId(categoryId);
        } else {
            return productRepository.findAll();
        }
    }}
