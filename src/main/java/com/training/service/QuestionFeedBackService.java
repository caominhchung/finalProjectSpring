package com.training.service;

import com.training.entities.FeedBack;
import com.training.entities.QuestionFeedBack;

import java.util.List;

/**
 * @author ChungCM
 */
public interface QuestionFeedBackService {
    void save(QuestionFeedBack questionFeedBack);
    List<QuestionFeedBack> findAllByFeedBack(FeedBack feedBack);
}
