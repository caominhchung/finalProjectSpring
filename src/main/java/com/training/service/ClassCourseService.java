package com.training.service;

import com.training.entities.Class;
import com.training.entities.ClassCourse;

import java.util.List;

public interface ClassCourseService {
    void saveAll(List<ClassCourse> classCourses);
    List<ClassCourse> findAllByClass(Class classs);
}
