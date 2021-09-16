package com.training.test;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) throws IOException {
        FileInputStream file = new FileInputStream(new File("/Users/lymin00/Desktop/Fsoft/Mockup/hn21_fr_java_02_g3/src/main/java/com/training/test/class_2021-09-06_09_21_10.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);
        Employee emp = new Employee();
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> itr = sheet.iterator();
        while(itr.hasNext()){
            Row row = itr.next();
//            emp.assignEmployee(row);
            //  enter code here for the rest operation
        }
    }
}
