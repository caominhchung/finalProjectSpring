package com.training.service.impl;

import com.training.entities.Class;
import com.training.entities.Trainee;
import com.training.entities.Trainer;
import com.training.entities.enumeration.ClassTypeName;
import com.training.entities.enumeration.StatusOfClass;
import com.training.repository.ClassRepository;
import com.training.repository.TraineeRepository;
import com.training.service.TraineeService;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class TraineeServiceImpl implements TraineeService {
    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    private ClassRepository classRepository;

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;


    @Override
    @Transactional
    public List<Trainee> findAll() {
        return traineeRepository.findAll();
    }

    @Override
    @Transactional
    public Trainee findById(Integer id) {
        return traineeRepository.findTraineeById(id);
    }

    @Transactional
    @Modifying
    @Override
    public void createTrainee(Trainee trainee){
        try {
            System.out.println("service");
            System.out.println(trainee.toString());
            traineeRepository.save(trainee);
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Trainee getTraineeByAccount(String account) {
        try {
            return traineeRepository.findTraineeByAccount(account);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Trainee getTraineeByEmail(String email) {
        try {
            return traineeRepository.findTraineeByEmail(email);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Trainee getTraineeByTelNumber(String telPhone) {
        try {
            return traineeRepository.findTraineeByTelNumber(telPhone);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Trainee getTraineeByFacebook(String facebook) {
        try {
            return traineeRepository.findTraineeByFacebook(facebook);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insertDataFromExcel(MultipartFile fileExcel) throws IOException {
        List<Trainee> trainees = new ArrayList<>();
        Trainee trainee = null;
        Class classOfTrainee = null;
        List<Class> classList = null;

        workbook = new XSSFWorkbook(fileExcel.getInputStream());
        sheet = workbook.getSheetAt(0);
        XSSFRow row = null;

        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            trainee = new Trainee();

            //set class
            if (row.getCell(5).getCellType() == CellType.NUMERIC) {
                classList = classRepository.findClassByName(String.valueOf(row.getCell(5).getNumericCellValue()));
            }
            else {
                classList = classRepository.findClassByName(row.getCell(5).getStringCellValue());
            }
            if (classList != null) {
                classOfTrainee = classList.get(0);
                trainee.setClassOfTrainee(classOfTrainee);
            }
            else {
                continue;
            }

            //set account
            if (row.getCell(0).getCellType() == CellType.NUMERIC) {
                trainee.setAccount(String.valueOf(row.getCell(0).getNumericCellValue()));
            }
            else {
                trainee.setAccount(row.getCell(0).getStringCellValue());
            }

            // set password
            if (row.getCell(1).getCellType() == CellType.NUMERIC) {
                trainee.setPassword(String.valueOf(row.getCell(1).getNumericCellValue()));
            }
            else {
                trainee.setPassword(row.getCell(1).getStringCellValue());
            }

            //set email
            if (row.getCell(2).getCellType() == CellType.NUMERIC) {
                trainee.setEmail(String.valueOf(row.getCell(2).getNumericCellValue()));
            }
            else {
                trainee.setEmail(row.getCell(2).getStringCellValue());
            }

            //set name
            if (row.getCell(3).getCellType() == CellType.NUMERIC) {
                trainee.setName(String.valueOf(row.getCell(3).getNumericCellValue()));
            }
            else {
                trainee.setName(row.getCell(3).getStringCellValue());
            }

            //set phone
            if (row.getCell(4).getCellType() == CellType.NUMERIC) {
                trainee.setTelNumber(String.valueOf(row.getCell(4).getNumericCellValue()));
            }
            else {
                trainee.setTelNumber(row.getCell(4).getStringCellValue());
            }

//            trainee.setGender(row.getCell(6).getStringCellValue());

            //set university
            if (row.getCell(6).getCellType() == CellType.NUMERIC) {
                trainee.setUniversity(String.valueOf(row.getCell(6).getNumericCellValue()));
            }
            else {
                trainee.setUniversity(row.getCell(6).getStringCellValue());
            }

            trainees.add(trainee);
        }
        traineeRepository.saveAll(trainees);
        workbook.close();
    }
}
