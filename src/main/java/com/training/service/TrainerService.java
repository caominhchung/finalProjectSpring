package com.training.service;

import com.training.entities.Trainer;

import java.util.List;

/**
 * @author tungns14
 */
public interface TrainerService {
    Trainer findById(Integer id);
    List<Trainer> findAll();
    Trainer findTrainerByAccount(String account);
    Trainer findTrainerByFacebook(String facebook);
    Trainer findTrainerByEmail(String email);
    Trainer findTrainerByTelNumber(String telNumber);
    void save(Trainer trainer);
}
