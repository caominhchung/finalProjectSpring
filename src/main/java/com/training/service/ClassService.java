package com.training.service;

import com.training.entities.Class;
import com.training.entities.Trainer;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface ClassService {

    List<Class> findAll();

    Page<Class> findAll(int pageNumber, int pageSize);

    Page<Class> findAllByTrainer(int pageNumber, int pageSize, Integer trainerId);

    boolean checkClassNameExist(String name);

    Class save(Class classs);

    Class findById(Integer id);


    void export(HttpServletResponse response) throws IOException;

    void insertDateFromExcel(MultipartFile fileExcel) throws IOException, ParseException;

    Class findClassByTrainer(Trainer trainer);

}
