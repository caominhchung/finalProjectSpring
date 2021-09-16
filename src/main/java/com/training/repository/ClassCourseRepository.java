package com.training.repository;

import com.training.entities.Class;
import com.training.entities.ClassCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassCourseRepository extends JpaRepository<ClassCourse, Integer> {
    List<ClassCourse> findAllByClasss(Class classs);
}
