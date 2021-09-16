package com.training.service;


import com.training.entities.Solution;

public interface SolutionService {

    public Solution getSolutionById(Integer id);
    public void updateSolution(Solution solution);
    public void deleteSolution(Solution solution);
    public void createSolution(Solution solution);
}
