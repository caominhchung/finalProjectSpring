package com.training.repository;

import com.training.entities.Mistake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tungns14
 */
@Repository
public interface MistakeRepository extends JpaRepository<Mistake, Integer> {

    Mistake findMistakeById(Integer id);
    List<Mistake> findAllByTraineeId(Integer traineeId);
}
