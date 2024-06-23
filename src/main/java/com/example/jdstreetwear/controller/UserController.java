package com.example.jdstreetwear.controller;

import com.example.jdstreetwear.model.Customer;
import com.example.jdstreetwear.model.Employee;
import com.example.jdstreetwear.model.User;
import com.example.jdstreetwear.service.CustomerService;
import com.example.jdstreetwear.service.EmployeeService;
import com.example.jdstreetwear.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        if (user.getRole().equals("employee")) {
            Employee employee = new Employee();
            employee.setUser(createdUser);
            employeeService.createEmployee(employee);
        } else if (user.getRole().equals("customer")) {
            Customer customer = new Customer();
            customer.setUser(createdUser);
            customerService.createCustomer(customer);
        }
        return createdUser;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);

        if (user.getRole().equals("employee")) {
            Optional<Employee> employeeOpt = employeeService.getEmployeeById(id);
            if (employeeOpt.isEmpty()) {
                Employee employee = new Employee();
                employee.setUser(updatedUser);
                employeeService.createEmployee(employee);
            } else {
                Employee employee = employeeOpt.get();
                employee.setUser(updatedUser);
                employeeService.updateEmployee(employee.getId(), employee);
            }
        } else if (user.getRole().equals("customer")) {
            Optional<Customer> customerOpt = customerService.getCustomerById(id);
            if (customerOpt.isEmpty()) {
                Customer customer = new Customer();
                customer.setUser(updatedUser);
                customerService.createCustomer(customer);
            } else {
                Customer customer = customerOpt.get();
                customer.setUser(updatedUser);
                customerService.updateCustomer(customer.getId(), customer);
            }
        }

        return updatedUser;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
