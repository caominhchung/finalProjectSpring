package com.training.controller;

import com.training.dto.ClassDto;
import com.training.entities.Class;
import com.training.entities.Fresher;
import com.training.entities.Trainee;
import com.training.entities.Trainer;
import com.training.entities.enumeration.ClassTypeName;
import com.training.entities.enumeration.InterviewStatus;
import com.training.entities.enumeration.StatusOfClass;
import com.training.entities.enumeration.StatusOfTrainee;
import com.training.repository.ClassRepository;
import com.training.service.ClassService;

import com.training.service.TraineeService;
import com.training.service.TrainerService;
import com.training.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * author longnb8
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(ClassController.class)
public class ClassControllerTest {

    @MockBean
    private ClassService classService;

    @MockBean
    private TrainerService trainerService;

    @MockBean
    private ClassRepository classRepository;

    @MockBean
    private TraineeService traineeService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_get_all_class() throws Exception {
        Class class1 = new Class();
        class1.setId(1);
        class1.setName("hn1");
        class1.setType(ClassTypeName.Fresher);
        class1.setStatusOfClass(StatusOfClass.Waiting);
        class1.setPlanCount(20);

        Trainer trainer = new Trainer();
        trainer.setId(1);
        trainer.setName("hoabt2");
        trainer.setAccount("hoabt2");
        trainer.setPassword("1234r");

        class1.setTrainer(trainer);

        Class class2 = new Class();
        class2.setId(2);
        class2.setName("hcm1");
        class2.setType(ClassTypeName.Internship);
        class2.setStatusOfClass(StatusOfClass.Done);
        class2.setPlanCount(10);

        Mockito.when(classService.findAll()).thenReturn(Arrays.asList(class1, class2));
        mockMvc.perform(get("/admin/class-management"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("classList", hasSize(2)))
                .andExpect(model().attribute("classList", hasItem(
                        allOf(
                                hasProperty("name", is("hn1")),
                                hasProperty("planCount", is(20)),
                                hasProperty("type", is(ClassTypeName.Fresher)),
                                hasProperty("statusOfClass", is(StatusOfClass.Waiting)),
                                hasProperty("trainer", is(trainer))


                        )
                )))
                .andExpect(model().attribute("classList", hasItem(
                        allOf(
                                hasProperty("name", is("hcm1")),
                                hasProperty("planCount", is(10))
                        )
                )));
        Mockito.verify(classService, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(classService);

    }

    @Test
    public void test_export_file() throws Exception {
        mockMvc.perform(get("/admin/class/export/excel").contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(status().isOk());

    }

    /**
     * author HuyLQ21
     *
     * @todo: this method use to test detail class screen
     */
    @Test
    public void test_detail_class() throws Exception {
        SimpleDateFormat sdfSource = new SimpleDateFormat("dd-MM-yyyy");
        List<Trainee> trainees = new ArrayList<>();
        List<Class> classes = new ArrayList<>();
        Fresher trainee = new Fresher();

        trainee.setFullTimeWorkingAvailable(sdfSource.parse("01-09-2021"));
        trainee.setUniversity("HUS");
//        trainee.setInterviewDate(sdfSource.parse("20-08-2021"));
        trainee.setStatus(StatusOfTrainee.Dropout);
        trainee.setAccount("hoabt2");
        trainee.setPassword("11111111");
        trainee.setName("Huy123");
        trainee.setEmail("Huykks123@gmail.com");

        Fresher trainee2 = new Fresher();
        trainee2.setFullTimeWorkingAvailable(sdfSource.parse("01-09-2021"));
        trainee2.setUniversity("HUS");
        trainee2.setStatus(StatusOfTrainee.StillLearning);
//        trainee2.setInterviewDate(sdfSource.parse("20-08-2021"));
        trainee2.setAccount("hoabt3");
        trainee2.setPassword("11111111");

        Fresher trainee3 = new Fresher();
        trainee3.setFullTimeWorkingAvailable(sdfSource.parse("01-09-2021"));
        trainee3.setUniversity("HUS");
        trainee3.setStatus(StatusOfTrainee.StillLearning);
//        trainee3.setInterviewStatus(InterviewStatus.Failed);
//        trainee3.setInterviewDate(sdfSource.parse("20-08-2021"));
        trainee3.setAccount("hoabt4");
        trainee3.setPassword("11111111");


        Fresher trainee4 = new Fresher();
        trainee4.setFullTimeWorkingAvailable(sdfSource.parse("01-09-2021"));
        trainee4.setUniversity("HUS");
        trainee4.setStatus(StatusOfTrainee.StillLearning);
//        trainee4.setInterviewStatus(InterviewStatus.Passed);
//        trainee4.setInterviewDate(sdfSource.parse("20-08-2021"));
        trainee4.setAccount("hoabt5");
        trainee4.setPassword("11111111");


        Trainer trainer = new Trainer();
        trainer.setId(1);
        trainer.setName("hoabt20");
        trainer.setAccount("trainer1");
        trainer.setPassword("123121321");
        trainer.setTelNumber("0966621846");
        trainer.setEmail("Huykks@gmail.com");


        Date openDate = sdfSource.parse("01-06-2021");
        Date endDate = sdfSource.parse("02-08-2021");

        Class class1 = new Class();
        class1.setId(1);
        class1.setStatusOfClass(StatusOfClass.Done);
        class1.setType(ClassTypeName.Fresher);
        class1.setName("class1");
        class1.setPlanCount(20);
        class1.setNote("none");
        class1.setStartDate(openDate);
        class1.setEndDate(endDate);

        trainee.setClassOfTrainee(class1);
        trainee2.setClassOfTrainee(class1);
        trainee3.setClassOfTrainee(class1);
        trainee4.setClassOfTrainee(class1);


        trainees.add(trainee);
        trainees.add(trainee2);
        trainees.add(trainee3);
        trainees.add(trainee4);
        class1.setTrainees(trainees);

        classes.add(class1);
        trainer.setClasses(classes);
        class1.setTrainer(trainer);

        Class class2 = new Class();
        class2.setId(2);
        class2.setName("hcm1");
        class2.setStartDate(openDate);
        class2.setEndDate(endDate);
        class2.setType(ClassTypeName.Internship);
        class2.setStatusOfClass(StatusOfClass.Waiting);
        class2.setPlanCount(10);

        Mockito.when(classService.findById(1)).thenReturn(class1);
        mockMvc.perform(get("/admin/class-management/{classId}", 1))
                .andExpect(status().isOk())
                .andExpect(model().attribute("class", hasProperty("id", is(1))))
                .andExpect(model().attribute("class", hasProperty("statusOfClass", is(StatusOfClass.Done))))
                .andExpect(model().attribute("class", hasProperty("type", is(ClassTypeName.Fresher))))
                .andExpect(model().attribute("class", hasProperty("name", is("class1"))))
                .andExpect(model().attribute("class", hasProperty("planCount", is(20))))
                .andExpect(model().attribute("class", hasProperty("note", is("none"))))
                .andExpect(model().attribute("class", hasProperty("startDate", is(sdfSource.parse("01-06-2021")))))
                .andExpect(model().attribute("class", hasProperty("endDate", is(sdfSource.parse("02-08-2021")))))
                .andExpect(model().attribute("class", hasProperty("trainees" , hasSize(4))))
                .andExpect(model().attribute("class", hasProperty("trainees", hasItem(
                        allOf(
                                hasProperty("name", is("Huy123")),
                                hasProperty("account", is("hoabt2")),
                                hasProperty("email", is("Huykks123@gmail.com")),
                                hasProperty("university", is("HUS"))
                        )
                ))))

                .andExpect(model().attribute("class", hasProperty("trainees", hasItem(
                        allOf(
                                hasProperty("account", is("hoabt3")),
                                hasProperty("university", is("HUS"))
                        )
                ))))
                .andExpect(model().attribute("class", hasProperty("trainees", hasItem(
                        allOf(
                                hasProperty("account", is("hoabt4")),
                                hasProperty("university", is("HUS"))
                        )
                ))))

                .andExpect(model().attribute("class", hasProperty("trainees", hasItem(
                        allOf(
                                hasProperty("account", is("hoabt5")),
                                hasProperty("university", is("HUS"))
                        )
                ))))
                .andExpect(model().attribute("class", hasProperty("trainer", hasProperty("account", is("trainer1")))))
                .andExpect(model().attribute("class", hasProperty("trainer", hasProperty("name", is("hoabt20")))))
                .andExpect(model().attribute("class", hasProperty("trainer", hasProperty("telNumber", is("0966621846")))))
                .andExpect(model().attribute("class", hasProperty("trainer", hasProperty("email", is("Huykks@gmail.com")))))
                .andExpect(model().attribute("countPassed", is(1)))
                .andExpect(model().attribute("countFailed", is(1)))
                .andExpect(model().attribute("countDropout", is(1)))

        ;
        Mockito.verify(classService, Mockito.times(1)).findById(1);
        Mockito.verifyNoMoreInteractions(classService);

    }



}