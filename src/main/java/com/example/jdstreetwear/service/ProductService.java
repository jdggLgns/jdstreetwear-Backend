package com.example.jdstreetwear.service;

import com.example.jdstreetwear.daos.ProductDAO;
import com.example.jdstreetwear.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDAO productDAO;

    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    public Product getProductById(Long id) {
        return productDAO.findById(id).orElse(null);
    }

    public Product createProduct(Product product) {
        return productDAO.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productDAO.findById(id).orElse(null);
        if (product != null) {
            product.setName(productDetails.getName());
            product.setProductCode(productDetails.getProductCode());
            product.setColor(productDetails.getColor());
            product.setSize(productDetails.getSize());
            product.setPrice(productDetails.getPrice());
            product.setType(productDetails.getType());
            product.setCategory(productDetails.getCategory());
            product.setDiscount(productDetails.getDiscount());
            product.setSupplier(productDetails.getSupplier());
            product.setInventories(productDetails.getInventories());
            product.setTshirt(productDetails.getTshirt());
            product.setSweatshirt(productDetails.getSweatshirt());
            product.setImage(productDetails.getImage());
            return productDAO.save(product);
        } else {
            return null;
        }
    }

    public void deleteProduct(Long id) {
        productDAO.deleteById(id);
    }

    public List<Product> searchProducts(String name) {
        return productDAO.findByName(name);
    }
}
