package com.training.service.impl;

import com.training.entities.Class;
import com.training.entities.Trainer;
import com.training.entities.enumeration.ClassTypeName;
import com.training.entities.enumeration.Role;
import com.training.entities.enumeration.StatusOfClass;
import com.training.repository.ClassRepository;
import com.training.service.ClassService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassRepository classRepository;

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    @Override
    @Transactional
    public List<Class> findAll() {
        return classRepository.findAll();
    }

    @Override
    @Transactional
    public Page<Class> findAll(int pageNumber, int pageSize) {
        return classRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("id").descending()));
    }

    @Override
    @Transactional
    public Page<Class> findAllByTrainer(int pageNumber, int pageSize, Integer trainerId) {
        return classRepository.findClassByTrainerId(PageRequest.of(pageNumber, pageSize, Sort.by("id").descending()), trainerId);
    }

    @Override
    @Transactional
    public boolean checkClassNameExist(String name) {
        return classRepository.findClassByName(name).size() > 0;
    }

    @Override
    @Transactional
    public Class save(Class classs) {
        return classRepository.save(classs);
    }

    @Override
    @Transactional
    public Class findById(Integer id) {
        return classRepository.findById(id).orElse(null);
    }


//    public String validate

    private void writeHeaderLine() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("class_management" + (int)Math.random()*10000);

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();

        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Class ID", style);
        createCell(row, 1, "Class Name", style);
        createCell(row, 2, "Plan count", style);
        createCell(row, 3, "Start Date", style);
        createCell(row, 4, "End Date", style);
        createCell(row, 5, "Type", style);
        createCell(row, 6, "Status", style);
        createCell(row, 7, "Trainer", style);
        createCell(row, 8, "TrainerID", style);
        createCell(row, 9, "TrainerAccount", style);
        createCell(row, 10, "TrainerPassword", style);
        createCell(row, 11, "Note", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines(List<Class> classes) {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Class classs : classes) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, classs.getId(), style);
            createCell(row, columnCount++, classs.getName(), style);
            createCell(row, columnCount++, classs.getPlanCount(), style);
            createCell(row, columnCount++, classs.getStartDate().toString(), style);
            createCell(row, columnCount++, classs.getEndDate().toString(), style);
            createCell(row, columnCount++, classs.getType().toString(), style);
            createCell(row, columnCount++, classs.getStatusOfClass().toString(), style);
            if(classs.getTrainer() != null) {
                createCell(row, columnCount++, classs.getTrainer().getName(), style);
                createCell(row, columnCount++, classs.getTrainer().getId(), style);
                createCell(row, columnCount++, classs.getTrainer().getAccount(), style);
                createCell(row, columnCount++, classs.getTrainer().getPassword(), style);
            }

            createCell(row, columnCount++, classs.getNote(), style);

        }
    }

    @Override
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines(findAll());
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    @Override
    public void insertDateFromExcel(MultipartFile fileExcel) throws IOException, ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        List<Class> classes = new ArrayList<>();
        workbook = new XSSFWorkbook(fileExcel.getInputStream());
        sheet = workbook.getSheetAt(0);
        Class classs = null;
        Trainer trainer = null;
        XSSFRow row = null;
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            classs = new Class();
            trainer = new Trainer();
            row = sheet.getRow(i);
            classs.setId((int)row.getCell(0).getNumericCellValue());
            classs.setName(row.getCell(1).getStringCellValue());
            classs.setPlanCount((int) row.getCell(2).getNumericCellValue());
            classs.setStartDate(formatDate.parse(row.getCell(3).getStringCellValue()));
            classs.setEndDate(formatDate.parse(row.getCell(4).getStringCellValue()));
            classs.setType(ClassTypeName.valueOf(row.getCell(5).getStringCellValue()));
            classs.setStatusOfClass(StatusOfClass.valueOf(row.getCell(6).getStringCellValue()));
            if(!"".equals(row.getCell(7).getStringCellValue())) {
                trainer.setName(row.getCell(7).getStringCellValue());
                trainer.setId((int)row.getCell(8).getNumericCellValue());
                trainer.setAccount(row.getCell(9).getStringCellValue());
                trainer.setPassword(row.getCell(10).getStringCellValue());
                trainer.setRole(Role.ROLE_TRAINER);
                classs.setTrainer(trainer);
            }

            classs.setNote((row.getCell(11) != null)? row.getCell(11).getStringCellValue() : "");
            classes.add(classs);
        }
        classRepository.saveAll(classes);
        workbook.close();


    }

    @Override
    public Class findClassByTrainer(Trainer trainer) {
        return classRepository.findClassByTrainer(trainer);
    }

}
