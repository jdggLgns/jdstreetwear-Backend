package com.example.jdstreetwear.service;

import com.example.jdstreetwear.model.Customer;
import com.example.jdstreetwear.model.User;
import com.example.jdstreetwear.repository.CustomerRepository;
import com.example.jdstreetwear.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        User user = customer.getUser();
        user.setRole("customer");
        userRepository.save(user);
        customer.setUser(user);
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            Customer updatedCustomer = customer.get();
            updatedCustomer.setPhone(customerDetails.getPhone());
            updatedCustomer.setAddress(customerDetails.getAddress());
            updatedCustomer.setWallet(customerDetails.getWallet());

            User updatedUser = updatedCustomer.getUser();
            updatedUser.setFirstName(customerDetails.getUser().getFirstName());
            updatedUser.setLastName(customerDetails.getUser().getLastName());
            updatedUser.setEmail(customerDetails.getUser().getEmail());
            updatedUser.setPassword(customerDetails.getUser().getPassword());

            userRepository.save(updatedUser);
            return customerRepository.save(updatedCustomer);
        } else {
            return null;
        }
    }
    public Customer getCustomerByUserId(Long userId) {
        return customerRepository.findByUserId(userId);
    }
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
