package com.training.service.impl;

import com.training.entities.Solution;
import com.training.repository.SolutionRepository;
import com.training.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolutionServiceImpl implements SolutionService {

    @Autowired
    private SolutionRepository solutionRepository;

    @Override
    public Solution getSolutionById(Integer id){
        return solutionRepository.getById(id);
    };

    @Override
    public void updateSolution(Solution solution){
        solutionRepository.save(solution);
    }

    @Override
    public void deleteSolution(Solution solution){
        solutionRepository.delete(solution);
    }

    @Override
    public void createSolution(Solution solution){
        solutionRepository.save(solution);
    }

}
