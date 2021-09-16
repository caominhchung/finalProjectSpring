package com.training.repository;

import com.training.entities.Course;
import com.training.entities.CourseTrainee;
import com.training.entities.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ChungCM
 */
@Repository
public interface CourseTraineeRepository extends JpaRepository<CourseTrainee, Integer> {
    List<CourseTrainee> findAllByCourse(Course course);
    CourseTrainee findByCourseAndTrainee(Course course, Trainee trainee);
}
