package com.example.jdstreetwear.repository;

import com.example.jdstreetwear.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
