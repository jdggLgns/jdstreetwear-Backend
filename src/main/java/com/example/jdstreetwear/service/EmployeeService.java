package com.example.jdstreetwear.service;

import com.example.jdstreetwear.daos.EmployeeDAO;
import com.example.jdstreetwear.model.User;
import com.example.jdstreetwear.repository.EmployeeRepository;
import com.example.jdstreetwear.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.jdstreetwear.model.Employee;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(Employee employee) {
        User user = employee.getUser();
        user.setRole("employee");
        userRepository.save(user);
        employee.setUser(user);
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            Employee updatedEmployee = employee.get();
            updatedEmployee.setJob(employeeDetails.getJob());
            updatedEmployee.setPhone(employeeDetails.getPhone());

            User updatedUser = updatedEmployee.getUser();
            updatedUser.setFirstName(employeeDetails.getUser().getFirstName());
            updatedUser.setLastName(employeeDetails.getUser().getLastName());
            updatedUser.setEmail(employeeDetails.getUser().getEmail());
            updatedUser.setPassword(employeeDetails.getUser().getPassword());

            userRepository.save(updatedUser);
            return employeeRepository.save(updatedEmployee);
        } else {
            return null;
        }
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
