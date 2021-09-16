package com.training.repository;

import com.training.entities.CourseTrainee;
import com.training.entities.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ChungCM
 */
@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, Integer> {
    FeedBack findByTrainingObjective(CourseTrainee courseTrainee);
    FeedBack findFeedBackById(Integer feedBackId);
}
