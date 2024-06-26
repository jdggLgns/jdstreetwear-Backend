package com.example.jdstreetwear.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.jdstreetwear.dao.SupplierDAO;
import com.example.jdstreetwear.model.Supplier;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierDAO supplierDAO;

    public List<Supplier> getAllSuppliers() {
        return supplierDAO.findAll();
    }

    public Optional<Supplier> getSupplierById(Long id) {
        return supplierDAO.findById(id);
    }

    public Supplier createSupplier(Supplier supplier) {
        return supplierDAO.save(supplier);
    }

    public Supplier updateSupplier(Long id, Supplier supplierDetails) {
        Optional<Supplier> optionalSupplier = supplierDAO.findById(id);

        if (optionalSupplier.isPresent()) {
            Supplier supplier = optionalSupplier.get();
            supplier.setName(supplierDetails.getName());
            supplier.setPhone(supplierDetails.getPhone());
            return supplierDAO.save(supplier);
        } else {
            throw new RuntimeException("Supplier not found with id " + id);
        }
    }

    public void deleteSupplier(Long id) {
        supplierDAO.deleteById(id);
    }
}
