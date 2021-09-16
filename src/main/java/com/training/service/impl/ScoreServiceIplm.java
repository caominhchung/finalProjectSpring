package com.training.service.impl;

import com.training.entities.Score;
import com.training.entities.enumeration.Rate;
import com.training.repository.ScoreRepository;
import com.training.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScoreServiceIplm implements ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Transactional
    @Override
    public void createScore(Score score) {
        scoreRepository.save(score);
    }

    @Override
    public Rate getRank(Double score) {
        if(score<=10.0 && score>=9.0){
            return Rate.AA;
        }else if (score<9.0&&score>=8.0){
            return Rate.A;
        }else if(score<8.0&&score>=6.0){
            return Rate.B;
        }else if(score<6.0&&score>=4.0){
            return Rate.C;
        }else if(score<4.0){
            return Rate.D;
        }

        return null;
    }

    @Override
    public Score findScoreById(Integer id){
        return scoreRepository.findById(id).get();
    }
}
