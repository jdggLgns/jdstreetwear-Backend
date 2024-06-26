package com.example.jdstreetwear.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.example.jdstreetwear.model.Discount;
import com.example.jdstreetwear.repository.DiscountRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class DiscountDAO {

    @Autowired
    private DiscountRepository discountRepository;

    public List<Discount> findAll() {
        return discountRepository.findAll();
    }

    public Optional<Discount> findById(Long id) {
        return discountRepository.findById(id);
    }

    public Discount save(Discount discount) {
        return discountRepository.save(discount);
    }

    public void deleteById(Long id) {
        discountRepository.deleteById(id);
    }
}
