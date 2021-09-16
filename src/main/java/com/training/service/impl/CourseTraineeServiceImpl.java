package com.training.service.impl;

import com.training.entities.Course;
import com.training.entities.CourseTrainee;
import com.training.repository.CourseTraineeRepository;
import com.training.entities.Trainee;
import com.training.service.CourseTraineeService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author ChungCM
 */
@Service
public class CourseTraineeServiceImpl implements CourseTraineeService {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<CourseTrainee> objectiveList;

    @Autowired
    private Course course;

    public CourseTraineeServiceImpl(Course course, List<CourseTrainee> objectiveList) {
            this.course = course;
            this.objectiveList = objectiveList;
            workbook = new XSSFWorkbook();
    }

    @Autowired
    private CourseTraineeRepository courseTraineeRepository;


    @Override
    public CourseTrainee findCourseTraineeByCourseAndTrainee(Course course, Trainee trainee) {
        return courseTraineeRepository.findByCourseAndTrainee(course, trainee);
    }


    @Override
    public List<CourseTrainee> findAllByCourse(Course course) {
        return courseTraineeRepository.findAllByCourse(course);
    }

    @Override
    public void export(HttpServletResponse response) throws IOException {
        writeInfoCourse();
        writeDataLine();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }

    private void writeInfoCourse() {
        sheet = workbook.createSheet("Subject_Detail_" + course.getId());
        Row row_1 = sheet.createRow(0);
        Row row_2 = sheet.createRow(1);
        Row row_3 = sheet.createRow(2);
        Row row_4 = sheet.createRow(3);
        Row row_5_1 = sheet.createRow(4);
        Row row_5 = sheet.createRow(5);
        Row row_5_2 = sheet.createRow(6);
        Row row_6 = sheet.createRow(7);

        CellStyle style_1 = workbook.createCellStyle();
        XSSFFont font_1 = workbook.createFont();
        font_1.setBold(true);
        font_1.setFontHeight(18);
        style_1.setFont(font_1);

        CellStyle style_2 = workbook.createCellStyle();
        XSSFFont font_2 = workbook.createFont();
        font_2.setFontHeight(16);
        style_2.setFont(font_2);

        CellStyle style_3 = workbook.createCellStyle();
        XSSFFont font_3 = workbook.createFont();
        font_3.setBold(true);
        font_3.setItalic(true);
        font_3.setFontHeight(16);
        style_3.setFont(font_3);

        CellStyle style_4 = workbook.createCellStyle();
        XSSFFont font_4 = workbook.createFont();
        font_4.setBold(true);
        font_4.setFontHeight(16);
        style_4.setFont(font_4);

        createCell(row_1, 0, "Subject:", style_1);
        createCell(row_1, 1, course.getName(), style_2);

        createCell(row_2, 0, "Duration:", style_1);
        createCell(row_2, 1, course.getDuration() + " days", style_2);

        createCell(row_3, 0, "Lecture:", style_1);
        createCell(row_3, 1, course.getLecture(), style_2);

        createCell(row_4, 0, "Description:", style_1);
        createCell(row_4, 1, course.getDescription(), style_2);

        createCell(row_5_1, 0, "", style_1);

        createCell(row_5, 0, "List Trainee", style_3);

        createCell(row_5_2, 0, "", style_1);


        createCell(row_6, 0, "No", style_4);
        createCell(row_6, 1, "Full Name", style_4);
        createCell(row_6, 2, "Class Name", style_4);
        createCell(row_6, 3, "Note", style_4);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        }
        else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLine() {
        int rowCount = 8;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        int no = 0;

        for (CourseTrainee objective : objectiveList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, ++no, style);
            createCell(row, columnCount++, objective.getTrainee().getName(), style);
            createCell(row, columnCount++, objective.getTrainee().getClassOfTrainee().getName(), style);
            createCell(row, columnCount++, objective.getTrainee().getNote(), style);
        }
    }


    @Transactional
    @Override
    public CourseTrainee getCourseTraineeById(Integer id){
        return courseTraineeRepository.findById(id).get();

    }

    @Transactional
    @Override
    public void createCourseTrainee(CourseTrainee courseTrainee){
        courseTraineeRepository.save(courseTrainee);
    }


}
