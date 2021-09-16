package com.training.service.impl;

import com.training.entities.Course;
import com.training.repository.CourseRepository;
import com.training.service.CourseService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author ChungCM
 */
@Service
public class CourseServiceImpl implements CourseService {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Course> courses;

    public CourseServiceImpl(List<Course> courses) {
        this.courses = courses;
        workbook = new XSSFWorkbook();
    }

    @Autowired
    private CourseRepository courseRepository;


    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course findCourseById(Integer courseId) {
        return courseRepository.getById(courseId);
    }

    @Override
    public Course findCourseByName(String name) {
        return courseRepository.findByName(name);
    }

    @Override
    public boolean isExistedCourse(String name) {
        return courseRepository.findByName(name) != null;
    }

    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }




    @Override
    public void export(HttpServletResponse response) throws IOException {
        writeHeadLine();
        writeDataLine();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }

    private void writeHeadLine() {
        sheet = workbook.createSheet("Subjects");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(18);
        style.setFont(font);
        createCell(row, 0, "Name", style);
        createCell(row, 1, "Duration", style);
        createCell(row, 2, "Lecture", style);
        createCell(row, 3, "Description", style);

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
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Course course : courses) {
            Row row = sheet.createRow(rowCount ++);
            int columnCount = 0;

            createCell(row, columnCount++, course.getName(), style);
            createCell(row, columnCount++, course.getDuration(), style);
            createCell(row, columnCount++, course.getLecture(), style);
            createCell(row, columnCount++, course.getDescription(), style);
        }
    }
}
