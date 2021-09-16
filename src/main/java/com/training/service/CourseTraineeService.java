package com.training.service;

import com.training.entities.Course;
import com.training.entities.CourseTrainee;
import com.training.entities.Trainee;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author ChungCM
 */

public interface CourseTraineeService {
    CourseTrainee findCourseTraineeByCourseAndTrainee(Course course, Trainee trainee);
    List<CourseTrainee> findAllByCourse(Course course);
    void export(HttpServletResponse response) throws IOException;

    public CourseTrainee getCourseTraineeById(Integer id);

    public void createCourseTrainee(CourseTrainee courseTrainee);
}
