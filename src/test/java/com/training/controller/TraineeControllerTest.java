package com.training.controller;

import com.training.dto.CreateTraineeDto;
import com.training.entities.Class;
import com.training.entities.Trainee;
import com.training.entities.User;
import com.training.entities.enumeration.ClassTypeName;
import com.training.entities.enumeration.StatusOfClass;
import com.training.repository.ClassRepository;
import com.training.service.ClassService;
import com.training.service.TraineeService;
import com.training.service.TrainerService;
import com.training.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * author HuyLQ21
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(TraineeController.class)
public class TraineeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClassRepository classRepository;

    @MockBean
    private ClassService classService;

    @MockBean
    private TraineeService traineeService;

    @MockBean
    private TrainerService trainerService;

    @MockBean
    private UserService userService;

    /**
     * author HuyLQ21
     *
     * @todo: this method use to test get form create trainee for class
     */
    @Test
    public void test_get_form_create_trainee_for_class() throws Exception {

        SimpleDateFormat sdfSource = new SimpleDateFormat("dd-MM-yyyy");
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

        Mockito.when(classRepository.findById(1)).thenReturn(java.util.Optional.of(class1));

        mockMvc.perform(get("/admin/class-management/{classId}/add-trainee", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("form_add_trainee"));
    }

    /**
     * author HuyLQ21
     *
     * @todo: this method use to test create trainee successful
     */
    @Test
    public void test_create_trainee_success() throws Exception {
        SimpleDateFormat sdfSource = new SimpleDateFormat("dd-MM-yyyy");
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
        List<Trainee> trainees = new ArrayList<>();
        trainees.add(new Trainee());
        class1.setTrainees(trainees);

        Mockito.when(classService.findById(1)).thenReturn(class1);

        CreateTraineeDto createTraineeDto = new CreateTraineeDto("vn", "HuyLQ212", "123456", "Huy Lekks", "09666218467", "123456", "Account24@gmail.com", "male", "Vn", "123456");

        mockMvc.perform(post("/admin/class-management/{classId}/add-trainee", 1)
                .param("national", "vn")
                .param("university", "hust")
                .param("account", "hihi")
                .param("password", "123456")
                .param("name", "Huy Le")
                .param("telNumber", "0966621846")
                .param("facebook", "123456")
                .param("email", "Account2@gmail.com")
                .param("gender", "male")
                .param("note", "none")
        )

                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/class-management/1"));

    }

    /**
     * author HuyLQ21
     *
     * @todo: this method use to test create trainee fail because account already exists in database
     */
    @Test
    public void test_create_trainee_fail_duplicate_account() throws Exception {
        SimpleDateFormat sdfSource = new SimpleDateFormat("dd-MM-yyyy");
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
        List<Trainee> trainees = new ArrayList<>();
        trainees.add(new Trainee());
        class1.setTrainees(trainees);

        Mockito.when(classService.findById(1)).thenReturn(class1);

        CreateTraineeDto createTraineeDto = new CreateTraineeDto("vn", "HuyLQ212", "123456", "Huy Lekks", "09666218467", "123456", "Account24@gmail.com", "male", "Vn", "123456");

        when(userService.findUserByAccount("HuyLQ123")).thenReturn(new User());

        mockMvc.perform(post("/admin/class-management/{classId}/add-trainee", 1)
                .param("national", "vn")
                .param("university", "hust")
                .param("account", "HuyLQ123")
                .param("password", "123456")
                .param("name", "Huy Le")
                .param("telNumber", "0966621846")
                .param("facebook", "123456")
                .param("email", "Account2@gmail.com")
                .param("gender", "male")
                .param("note", "none")
        )

                .andExpect(view().name("form_add_trainee"))
                .andExpect(model().attribute("errorAccount",is("Account existed") ));

    }

    /**
     * author HuyLQ21
     *
     * @todo: this method use to test create trainee fail because email already exists in database
     */
    @Test
    public void test_create_trainee_fail_duplicate_email() throws Exception {
        SimpleDateFormat sdfSource = new SimpleDateFormat("dd-MM-yyyy");
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
        List<Trainee> trainees = new ArrayList<>();
        trainees.add(new Trainee());
        class1.setTrainees(trainees);

        Mockito.when(classService.findById(1)).thenReturn(class1);

        CreateTraineeDto createTraineeDto = new CreateTraineeDto("vn", "HuyLQ212", "123456", "Huy Lekks", "09666218467", "123456", "Account24@gmail.com", "male", "Vn", "123456");

        when(traineeService.getTraineeByEmail("Account2@gmail.com")).thenReturn(new Trainee());

        mockMvc.perform(post("/admin/class-management/{classId}/add-trainee", 1)
                .param("national", "vn")
                .param("university", "hust")
                .param("account", "HuyLQ123")
                .param("password", "123456")
                .param("name", "Huy Le")
                .param("telNumber", "0966621846")
                .param("facebook", "123456")
                .param("email", "Account2@gmail.com")
                .param("gender", "male")
                .param("note", "none")
        )

                .andExpect(view().name("form_add_trainee"))
                .andExpect(model().attribute("errorEmail",is("Email existed") ));

    }
}