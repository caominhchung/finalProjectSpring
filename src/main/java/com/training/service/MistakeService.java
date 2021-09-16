package com.training.service;

import com.training.entities.Mistake;
import java.util.List;

/**
 * @author tungns14
 */
public interface MistakeService {
    Mistake findMistakeById(Integer id);
    List<Mistake> findAllByTraineeId(Integer traineeId);
    void save(Mistake mistake);
}
