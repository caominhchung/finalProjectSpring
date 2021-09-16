package com.training.repository;

import com.training.entities.FeedBack;
import com.training.entities.QuestionFeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ChungCM
 */
@Repository
public interface QuestionFeedBackRepository extends JpaRepository<QuestionFeedBack, Integer> {
    List<QuestionFeedBack> findAllByFeedBack(FeedBack feedBack);
}
