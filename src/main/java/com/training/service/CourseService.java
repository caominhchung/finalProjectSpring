package com.training.service;

import com.training.entities.Course;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author ChungCM
 */
public interface CourseService {
    List<Course> findAll();
    Course findCourseById(Integer courseId);
    Course findCourseByName(String name);
    boolean isExistedCourse(String name);
    void save(Course course);
    void export(HttpServletResponse response) throws IOException;
}
