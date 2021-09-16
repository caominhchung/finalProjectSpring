package com.training.repository;

import com.training.entities.Fresher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FresherRepository extends JpaRepository<Fresher, Integer> {
}
