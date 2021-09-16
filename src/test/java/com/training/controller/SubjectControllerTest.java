package com.training.controller;

import com.training.entities.Course;
import com.training.entities.CourseTrainee;
import com.training.repository.CourseRepository;
import com.training.repository.CourseTraineeRepository;
import com.training.service.CourseService;
import com.training.service.CourseTraineeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author ChungCM
 */
@RunWith(SpringRunner.class)
@WebMvcTest(SubjectController.class)
public class SubjectControllerTest {

    @MockBean
    private CourseService courseService;

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private CourseTraineeService courseTraineeService;

    @MockBean
    private CourseTraineeRepository courseTraineeRepository;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void showListSubject() throws Exception {
        Course course1 = new Course();
        course1.setId(1);
        course1.setName("Java");
        course1.setLecture("ChungCM");
        course1.setDuration(7.0);
        course1.setDescription("This is java");

        Course course2 = new Course();
        course2.setId(2);
        course2.setName("C#");
        course2.setLecture("CMC");
        course2.setDuration(8.0);
        course2.setDescription("This is C#");

        Mockito.when(courseService.findAll()).thenReturn(Arrays.asList(course1,course2));
        mockMvc.perform(get("/admin/subject/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("courseList", hasSize(2)))
                .andExpect(model().attribute("courseList", hasItem(
                        allOf(
                                hasProperty("name", is("Java")),
                                hasProperty("lecture", is("ChungCM")),
                                hasProperty("duration", is(7.0)),
                                hasProperty("description", is("This is java"))
                        )
                )))
                .andExpect(model().attribute("courseList", hasItem(
                        allOf(
                                hasProperty("name", is("C#")),
                                hasProperty("lecture", is("CMC")),
                                hasProperty("duration", is(8.0)),
                                hasProperty("description", is("This is C#"))
                        )
                )));
        Mockito.verify(courseService, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(courseService);
    }

    @Test
    public void exportCourseListToExcel() throws Exception {
        mockMvc.perform(get("/admin/subject/list/export").contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(status().isOk());
    }

    @Test
    public void showFormAddSubject() throws Exception {
        mockMvc.perform(get("/admin/subject/list"))
                .andExpect(status().isOk());
    }

    @Test
    public void submitFormAddSubject() throws Exception {
        Course course1 = new Course();
        course1.setId(1);
        course1.setName("Java");
        course1.setLecture("ChungCM");
        course1.setDuration(7.0);
        course1.setDescription("This is java");

        Course course2 = new Course();
        Integer id = 2;
        String name = "Python";
        String lecture = "ChungCM";
        Double duration = 9.0;
        String description = "This is python";

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                ((Course)arguments[0]).setId(id);
                ((Course)arguments[0]).setName(name);
                ((Course)arguments[0]).setLecture(lecture);
                ((Course)arguments[0]).setDuration(duration);
                ((Course)arguments[0]).setDescription(description);
                return null;
            }
        }).when(courseService).save(course2);
        courseService.save(course2);
        mockMvc.perform(post("/admin/subject/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/subject/list"));

    }

    @Test
    public void showListTrainingObjective() throws Exception {
        Course course1 = new Course();
        course1.setId(1);
        course1.setName("Java");
        course1.setLecture("ChungCM");
        course1.setDuration(7.0);
        course1.setDescription("This is java");

        CourseTrainee objective1 = new CourseTrainee();
        objective1.setId(1);
        objective1.setCourse(course1);

        CourseTrainee objective2 = new CourseTrainee();
        objective1.setId(2);
        objective1.setCourse(course1);

        Mockito.when(courseTraineeService.findAllByCourse(course1)).thenReturn(Arrays.asList(objective1, objective2));
        mockMvc.perform(get("/admin/subject/"+ course1.getId() +"/list-training-objective"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("trainingObjList", hasSize(2)))
                .andExpect(model().attribute("trainingObjList", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("course", is(course1))
                        )
                )))
                .andExpect(model().attribute("trainingObjList", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("course", is(course1))
                        )
                )));
        Mockito.verify(courseTraineeService, Mockito.atLeastOnce());
        Mockito.verifyNoMoreInteractions(courseTraineeService);

    }

    @Test
    public void exportCourseDetailToExcel() throws Exception {
        Course course1 = new Course();
        course1.setId(1);
        course1.setName("Java");
        course1.setLecture("ChungCM");
        course1.setDuration(7.0);
        course1.setDescription("This is java");

        mockMvc.perform(get("/admin/subject/" + course1.getId() +"/list-training-objective/export").contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(status().isOk());


    }
}