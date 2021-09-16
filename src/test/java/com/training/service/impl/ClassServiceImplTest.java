package com.training.service.impl;

import com.training.dto.ClassDto;
import com.training.entities.Class;
import com.training.entities.Trainer;
import com.training.entities.enumeration.ClassTypeName;
import com.training.entities.enumeration.StatusOfClass;
import com.training.service.ClassService;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ClassServiceImplTest {

    @MockBean
    private ClassService classService;

    @MockBean
    private Class classs1;

    /**
     * author longnb8
     */
    @Test
    public void test_save_class() throws Exception {

        Class classDto = new Class();
        classDto.setName("java 01");
        classDto.setPlanCount(12);
        classDto.setStatusOfClass((StatusOfClass.Waiting));
        classDto.setType((ClassTypeName.Fresher));

        Trainer trainer = new Trainer();
        trainer.setId(1);
        trainer.setAccount("hoabt2");
        classDto.setTrainer(trainer);


        Mockito.when(classService.save(classDto)).thenReturn(classDto);
        classs1 = classService.save(classDto);
        Assert.assertEquals(classs1.getName(), classDto.getName());
        Mockito.verify(classService, Mockito.times(1)).save(classDto);

    }

    /**
     * author longnb8
     */
    @Test
    public void test_find_class_by_class_name() throws Exception {
        Mockito.when(classService.checkClassNameExist("hoabt2")).thenReturn(true);
        boolean check = classService.checkClassNameExist("hoabt2");
        Assert.assertTrue(check);
        Mockito.verify(classService, Mockito.times(1)).checkClassNameExist("hoabt2");
    }

    /**
     * author longnb8
     */
    @Test
    public void test_find_all() throws Exception {
        Class classDto = new Class();
        classDto.setName("java 01");
        classDto.setPlanCount(12);
        classDto.setStatusOfClass((StatusOfClass.Waiting));
        classDto.setType((ClassTypeName.Fresher));

        Class classDto1 = new Class();
        classDto1.setName("java 02");
        classDto1.setPlanCount(12);
        classDto1.setStatusOfClass((StatusOfClass.Waiting));
        classDto1.setType((ClassTypeName.Fresher));

        Trainer trainer = new Trainer();
        trainer.setId(1);
        trainer.setAccount("hoabt2");
        classDto.setTrainer(trainer);

        Mockito.when(classService.findAll()).thenReturn(Arrays.asList(classDto, classDto1));
        List<Class> classes = classService.findAll();
        Assert.assertEquals(2, classes.size());
        Mockito.verify(classService, Mockito.times(1)).findAll();
    }

    /**
     * author longnb8
     */
    @Test
    public void test_find_by_id() throws  Exception {
        Class classDto = new Class();
        classDto.setId(1);
        classDto.setName("java 01");
        classDto.setPlanCount(12);
        classDto.setStatusOfClass((StatusOfClass.Waiting));
        classDto.setType((ClassTypeName.Fresher));
        Mockito.when(classService.findById(1)).thenReturn(classDto);
        Class classs = classService.findById(1);
        Assert.assertEquals(classs.getName(), classDto.getName());
        Assert.assertEquals(classs.getId(), classDto.getId());
        Mockito.verify(classService, Mockito.times(1)).findById(1);
    }

    /**
     * author longnb8
     */
    @Test
    public void test_import_file() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file",
                "class.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                new ClassPathResource("excel/class.xlsx").getInputStream());

        Mockito.doNothing().when(classService).insertDateFromExcel(Mockito.isA(MultipartFile.class));
        classService.insertDateFromExcel(mockMultipartFile);
        Mockito.verify(classService, Mockito.times(1)).insertDateFromExcel(mockMultipartFile);
    }





}