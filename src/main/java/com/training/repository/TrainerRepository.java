package com.training.repository;

import com.training.entities.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {
//    Trainer findTrainerById(Integer id);
    List<Trainer> findAll();
    Trainer findTrainerByAccount(String account);
    Trainer findTrainerByFacebook(String facebook);
    Trainer findTrainerByEmail(String email);
    Trainer findTrainerByTelNumber(String telNumber);
}
