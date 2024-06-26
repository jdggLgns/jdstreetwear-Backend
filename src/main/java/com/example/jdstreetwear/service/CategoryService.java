package com.example.jdstreetwear.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.jdstreetwear.dao.CategoryDAO;
import com.example.jdstreetwear.model.Category;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryDAO categoryDAO;

    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryDAO.findById(id);
    }

    public Category createCategory(Category category) {
        return categoryDAO.save(category);
    }

    public Category updateCategory(Long id, Category categoryDetails) {
        Optional<Category> optionalCategory = categoryDAO.findById(id);

        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setName(categoryDetails.getName());
            return categoryDAO.save(category);
        } else {
            throw new RuntimeException("Category not found with id " + id);
        }
    }

    public void deleteCategory(Long id) {
        categoryDAO.deleteById(id);
    }
}
