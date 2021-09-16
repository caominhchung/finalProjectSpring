package com.training.service.impl;

import com.training.entities.Mistake;
import com.training.repository.MistakeRepository;
import com.training.service.MistakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tungns14
 */
@Service
public class MistakeServiceImpl implements MistakeService {
    @Autowired
    private MistakeRepository mistakeRepository;

    @Override
    @Transactional
    public Mistake findMistakeById(Integer id) {
        return mistakeRepository.findMistakeById(id);
    }

    @Override
    @Transactional
    public List<Mistake> findAllByTraineeId(Integer traineeId) {
        return mistakeRepository.findAllByTraineeId(traineeId);
    }

    @Override
    @Transactional
    public void save(Mistake mistake) {
        mistakeRepository.save(mistake);
    }

}
