package com.training.service.impl;

import com.training.entities.FeedBack;
import com.training.entities.QuestionFeedBack;
import com.training.repository.QuestionFeedBackRepository;
import com.training.service.QuestionFeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ChungCM
 */
@Service
public class QuestionFeedBackServiceImpl implements QuestionFeedBackService {

    @Autowired
    private QuestionFeedBackRepository questionFeedBackRepository;

    @Override
    public void save(QuestionFeedBack questionFeedBack) {
        questionFeedBackRepository.save(questionFeedBack);
    }

    @Override
    public List<QuestionFeedBack> findAllByFeedBack(FeedBack feedBack) {
        return questionFeedBackRepository.findAllByFeedBack(feedBack);
    }
}
