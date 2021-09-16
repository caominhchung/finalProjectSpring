package com.training.service.impl;

import com.training.entities.Class;
import com.training.entities.ClassCourse;
import com.training.repository.ClassCourseRepository;
import com.training.service.ClassCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClassCourseServiceImpl implements ClassCourseService {

    @Autowired
    private ClassCourseRepository classCourseRepository;

    @Override
    @Transactional
    public void saveAll(List<ClassCourse> classCourses) {
        classCourseRepository.saveAll(classCourses);
    }

    @Override
    public List<ClassCourse> findAllByClass(Class classs) {
        return classCourseRepository.findAllByClasss(classs);
    }
}
