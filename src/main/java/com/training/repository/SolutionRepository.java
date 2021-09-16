package com.training.repository;

import com.training.entities.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolutionRepository extends JpaRepository<Solution,Integer > {
}
