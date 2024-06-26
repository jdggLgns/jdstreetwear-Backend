package com.example.jdstreetwear.service;

import com.example.jdstreetwear.model.Discount;
import com.example.jdstreetwear.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    public Optional<Discount> getDiscountById(Long id) {
        return discountRepository.findById(id);
    }

    public Discount createDiscount(Discount discount) {
        return discountRepository.save(discount);
    }

    public Discount updateDiscount(Long id, Discount discountDetails) {
        Optional<Discount> optionalDiscount = discountRepository.findById(id);
        if (optionalDiscount.isPresent()) {
            Discount discount = optionalDiscount.get();
            discount.setName(discountDetails.getName());
            discount.setPercentage(discountDetails.getPercentage());
            discount.setState(discountDetails.getState());
            return discountRepository.save(discount);
        } else {
            throw new RuntimeException("Discount not found with id " + id);
        }
    }

    public void deleteDiscount(Long id) {
        discountRepository.deleteById(id);
    }
}
