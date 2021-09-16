package com.training.service.impl;

import com.training.entities.Course;
import com.training.entities.CourseTrainee;
import com.training.service.CourseTraineeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * @author ChungCM
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CourseTraineeServiceImpl.class)
public class TrainingObjectiveServiceImplTest {

    @MockBean
    private CourseTraineeService courseTraineeService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findAllByCourse() {
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
        Assert.assertEquals(2, courseTraineeService.findAllByCourse(course1).size());
    }

    @Test
    public void export() {
    }
}