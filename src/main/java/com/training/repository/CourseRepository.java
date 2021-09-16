package com.training.repository;

import com.training.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ChungCM
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Course findByName(String name);
}
