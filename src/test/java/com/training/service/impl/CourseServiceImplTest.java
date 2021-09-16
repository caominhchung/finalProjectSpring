package com.training.service.impl;

import com.training.entities.Course;
import com.training.repository.CourseRepository;
import com.training.service.CourseService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author ChungCM
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CourseServiceImpl.class)
public class CourseServiceImplTest {

    @MockBean
    private CourseRepository courseRepository;

    @Autowired
    private CourseService courseService;


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findAll() {

        Mockito.when(courseRepository.findAll()).thenReturn(Arrays.asList(new Course(), new Course()));
        List<Course> courses = courseService.findAll();
        Assert.assertEquals(2, courses.size());
    }

    @Test
    public void findCourseById() {
        Course course_1 = new Course();
        course_1.setId(1);
        course_1.setName("Java");
        course_1.setLecture("ChungCM");
        course_1.setDuration(9.0);
        course_1.setDescription("This is java subject");

        Mockito.when(courseService.findCourseById(1)).thenReturn(course_1);
        Assert.assertEquals(courseService.findCourseById(1), course_1);
        Mockito.verify(courseService, Mockito.times(1)).findCourseById(1);
    }

    @Test
    public void isExistedCourse() {
//        Course course_1 = new Course();
//        course_1.setId(1);
//        course_1.setName("Java");
//        course_1.setLecture("ChungCM");
//        course_1.setDuration(9.0);
//        course_1.setDescription("This is java subject");

        String name = "Java";

        Mockito.when(courseRepository.findByName(name)).thenReturn(new Course());
        Assert.assertTrue(courseService.isExistedCourse(name));
//        Mockito.verify(courseService, Mockito.times(1)).isExistedCourse(name);
    }

    @Test
    public void save() {
        Course course_1 = new Course();
        course_1.setId(1);
        course_1.setName("Java");
        course_1.setLecture("ChungCM");
        course_1.setDuration(9.0);
        course_1.setDescription("This is java subject");

        Course course_2 = new Course();
        Integer id = 2;
        String name = "Python";
        String lecture = "ChungCM";
        Double duration = 11.0;
        String description = "Python Subject";

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
        }).when(courseService).save(course_2);
        courseService.save(course_2);
        Assert.assertEquals(id, course_2.getId());
        Assert.assertEquals(name, course_2.getName());
        Assert.assertEquals(lecture, course_2.getLecture());
        Assert.assertEquals(duration, course_2.getDuration());
        Assert.assertEquals(description, course_2.getDescription());
        Mockito.verify(courseService, Mockito.times(1)).save(course_2);
    }

    @Test
    public void export() {
    }
}