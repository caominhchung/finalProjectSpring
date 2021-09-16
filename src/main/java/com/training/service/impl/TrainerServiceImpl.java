package com.training.service.impl;

import com.training.entities.Trainer;
import com.training.repository.TrainerRepository;
import com.training.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tungns14
 */
@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    private TrainerRepository trainerRepository;

    @Override
    @Transactional
    public List<Trainer> findAll() {
        return  trainerRepository.findAll();

    }

    @Override
    @Transactional
    public Trainer findById(Integer id) {
        return  trainerRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Trainer findTrainerByAccount(String account) {
        return trainerRepository.findTrainerByAccount(account);
    }

    @Override
    @Transactional
    public Trainer findTrainerByFacebook(String facebook) {
        return trainerRepository.findTrainerByFacebook(facebook);
    }

    @Override
    @Transactional
    public Trainer findTrainerByEmail(String email) {
        return trainerRepository.findTrainerByEmail(email);
    }

    @Override
    @Transactional
    public Trainer findTrainerByTelNumber(String telNumber) {
        return trainerRepository.findTrainerByTelNumber(telNumber);
    }

    @Override
    @Transactional
    public void save(Trainer trainer) {
        trainerRepository.save(trainer);
    }

}
