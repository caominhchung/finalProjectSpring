package com.training.service.impl;

import com.training.entities.CourseTrainee;
import com.training.entities.FeedBack;
import com.training.repository.FeedBackRepository;
import com.training.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChungCM
 */
@Service
public class FeedBackServiceImpl implements FeedBackService {

    @Autowired
    private FeedBackRepository feedBackRepository;

    @Override
    public void save(FeedBack feedBack) {
        feedBackRepository.save(feedBack);
    }

    @Override
    public FeedBack findFeedBackByCourseTrainee(CourseTrainee courseTrainee) {
        return feedBackRepository.findByTrainingObjective(courseTrainee);
    }

    @Override
    public FeedBack findFeedBackById(Integer feedBackId) {
        return feedBackRepository.findFeedBackById(feedBackId);
    }
}
