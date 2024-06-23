package com.example.jdstreetwear.repository;

import com.example.jdstreetwear.model.Tshirt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TshirtRepository extends JpaRepository<Tshirt, Long> {
}
