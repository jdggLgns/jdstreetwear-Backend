package com.example.jdstreetwear.repository;

import com.example.jdstreetwear.model.Sweatshirt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SweatshirtRepository extends JpaRepository<Sweatshirt, Long> {
}
