package com.training.service;

import com.training.entities.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface TraineeService {
    List<Trainee> findAll();

    Trainee findById(Integer id);

    public void createTrainee(Trainee trainee);

    public Trainee getTraineeByAccount(String account);

    public Trainee getTraineeByEmail(String email);

    public Trainee getTraineeByTelNumber(String telPhone);

    public Trainee getTraineeByFacebook(String facebook);

    void insertDataFromExcel(MultipartFile fileExcel) throws IOException, ParseException;

}