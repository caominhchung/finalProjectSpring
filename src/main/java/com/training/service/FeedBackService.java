package com.training.service;

import com.training.entities.CourseTrainee;
import com.training.entities.FeedBack;

/**
 * @author ChungCM
 */
public interface FeedBackService {
    void save(FeedBack feedBack);
    FeedBack findFeedBackByCourseTrainee(CourseTrainee courseTrainee);
    FeedBack findFeedBackById(Integer feedBackId);
}
