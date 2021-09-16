package com.training.service;

import com.training.entities.Score;
import com.training.entities.enumeration.Rate;

public interface ScoreService {

    public void createScore(Score score);

    public Rate getRank(Double score);

    public Score findScoreById(Integer id);
}
