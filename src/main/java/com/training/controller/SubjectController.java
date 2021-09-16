package com.training.controller;

import com.training.dto.CourseDto;
import com.training.entities.Course;
import com.training.entities.CourseTrainee;
import com.training.entities.FeedBack;
import com.training.entities.QuestionFeedBack;
import com.training.service.CourseService;
import com.training.service.CourseTraineeService;
import com.training.service.FeedBackService;
import com.training.service.QuestionFeedBackService;
import com.training.service.impl.CourseServiceImpl;
import com.training.service.impl.CourseTraineeServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


@Controller
@RequestMapping("/admin/subject")
public class SubjectController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseTraineeService courseTraineeService;

    @Autowired
    private FeedBackService feedBackService;

    @Autowired
    private QuestionFeedBackService questionFeedBackService;


    /**
     * Method showListSubject is used to show all Courses
     *
     * @author ChungCM
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String showListSubject(Model model) {
        model.addAttribute("courseList", courseService.findAll());
        return "subject-management";
    }


    /**
     * Method exportCourseListToExcel is used to export list courses to excel file.
     *
     * @author ChungCM
     * @param response
     * @throws IOException
     */
    @GetMapping("/list/export")
    public void exportCourseListToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDate = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; fileName=course_" + currentDate + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Course> courses = courseService.findAll();
        CourseService courseService = new CourseServiceImpl(courses);
        courseService.export(response);
    }

    /**
     * Method showFormAddSubject is used to show form to add new Course
     *
     * @author ChungCM
     * @param model
     * @return
     */
    @GetMapping("/add")
    public String showFormAddSubject(Model model) {
        model.addAttribute("course", new CourseDto());
        return "form-add-course";
    }

    /**
     * Method submitFormAddSubject is used to check and save a new course into database
     *
     * @author ChungCM
     * @param model
     * @param courseDto
     * @return
     */
    @PostMapping("/add")
    public String submitFormAddSubject(Model model,
                                       @ModelAttribute("course") CourseDto courseDto)
    {
        if (courseService.isExistedCourse(courseDto.getName())) {
            model.addAttribute("error","Sorry! Subject is existed!");
            model.addAttribute("course", new Course());
            return "form-add-course";
        } else {
            Course course = modelMapper.map(courseDto, Course.class);
            courseService.save(course);
            return "redirect:/admin/subject/list";
        }

    }

    /**
     * Method showListTrainingObjective is used to show detail a course
     *
     * @author ChungCM
     * @param model
     * @param courseId
     * @return
     */
    @GetMapping("/{courseId}/list-training-objective")
    public String showListTrainingObjective(@PathVariable("courseId")Integer courseId, Model model) {
        Course course = courseService.findCourseById(courseId);
        model.addAttribute("course", course);
        model.addAttribute("trainingObjList", courseTraineeService.findAllByCourse(course));
        return "detail-subject";
    }

    /**
     * Method showFormFeedBack is used to show list feed back for admin
     *
     * @author ChungCM
     * @param courseId
     * @param model
     * @return
     */
    @GetMapping("/{courseId}/feed-back")
    public String showFormFeedBack(@PathVariable("courseId")Integer courseId, Model model) {
        Course course = courseService.findCourseById(courseId);
        List<CourseTrainee> courseTraineeList = courseTraineeService.findAllByCourse(course);

        List<FeedBack> feedBackList = new ArrayList<>();
        FeedBack feedBack = null;
        for (CourseTrainee courseTrainee : courseTraineeList) {
            feedBack = feedBackService.findFeedBackByCourseTrainee(courseTrainee);
            feedBackList.add(feedBack);
        }
        Iterator<FeedBack> iterator = feedBackList.iterator();
        while (iterator.hasNext()) {
            feedBack = iterator.next();
            if (feedBack == null) {
                iterator.remove();
            }
        }
        model.addAttribute("course", course);
        model.addAttribute("feedBackList", feedBackList);


        return "view-feedback";
    }

    @GetMapping("/{courseId}/feed-back/{feedBackId}")
    public String showDetailFeedBack(@PathVariable("courseId")Integer courseId,
                                     @PathVariable("feedBackId")Integer feedBackId,
                                     Model model) {
        FeedBack feedBack = feedBackService.findFeedBackById(feedBackId);
        Course course = courseService.findCourseById(courseId);
        List<QuestionFeedBack> questionFeedBackList = questionFeedBackService.findAllByFeedBack(feedBack);
        model.addAttribute("feedBack", feedBack);
        model.addAttribute("course", course);
        model.addAttribute("questionFeedBackList", questionFeedBackList);
        return "view-detail-feedback";
    }


    /**
     * Method exportCourseDetailToExcel is used to export course detail to excel file.
     *
     * @author ChungCM
     * @param courseId
     * @param response
     * @throws IOException
     */
    @GetMapping("/{courseId}/list-training-objective/export")
    public void exportCourseDetailToExcel(
            @PathVariable("courseId")Integer courseId,
            HttpServletResponse response) throws IOException {
        Course course = courseService.findCourseById(courseId);
        List<CourseTrainee> objectiveList = this.courseTraineeService.findAllByCourse(course);
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; fileName=subject_detail_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        CourseTraineeService courseTraineeService = new CourseTraineeServiceImpl(course, objectiveList);
        courseTraineeService.export(response);
    }


}
