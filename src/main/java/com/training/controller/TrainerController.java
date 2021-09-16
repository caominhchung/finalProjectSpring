package com.training.controller;

import com.training.dto.TrainerDto;
import com.training.entities.*;
import com.training.entities.Class;
import com.training.entities.enumeration.Gender;
import com.training.entities.enumeration.Role;
import com.training.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author tungns14
 */
@Controller
@RequestMapping("/admin")
public class TrainerController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    TrainerService trainerService;

    @Autowired
    private ClassService classService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ClassCourseService classCourseService;

    @Autowired
    private CourseTraineeService courseTraineeService;

    @Autowired
    private FeedBackService feedBackService;

    @Autowired
    private QuestionFeedBackService questionFeedBackService;


    @GetMapping("/trainer-management")
    public String showTrainerManagement(Model model) {
        List<Trainer> trainers = trainerService.findAll();
        model.addAttribute("trainers", trainers);
        return "trainer-management";
    }

    @GetMapping("/add-trainer")
    public String showFormAddTrainer(Model model) {
        model.addAttribute("trainerDto", new TrainerDto());
        return "form-add-trainer";
    }

    @PostMapping("/add-trainer")
    public String submitFormAddTrainer(@Valid @ModelAttribute("trainerDto") TrainerDto trainerDto, BindingResult bindingResult, Model model) {
        boolean check = true;
        String account = trainerDto.getAccount();
        String email = trainerDto.getEmail();
        String facebook = trainerDto.getFacebook();
        String phone = trainerDto.getTelNumber();

        if (null != trainerService.findTrainerByAccount(account)) {
            model.addAttribute("errorAccount", "Account existed");
            check = false;
        }
        if (null != trainerService.findTrainerByTelNumber(phone)) {
            model.addAttribute("errorPhone", "Phone number existed");
            check = false;
        }

        if (null != trainerService.findTrainerByEmail(email)) {
            model.addAttribute("errorEmail", "Email existed");
            check = false;
        }

        if (null != trainerService.findTrainerByFacebook(facebook)) {
            model.addAttribute("errorFacebook", "facebook existed");
            check = false;
        }

        if (bindingResult.hasErrors()) {
            return "form-add-trainer";
        }

//        if(null == trainerService.findByAccount(trainerDto.getAccount())){
//        model.addAttribute("error","Trainer account existed");
//        model.addAttribute("trainer", new TrainerDto());
//        return "form-add-trainer";
//        }
        if (check) {
            Trainer trainer = modelMapper.map(trainerDto, Trainer.class);
            trainer.setPassword(trainer.getPassword());
            trainer.setRole(Role.ROLE_TRAINER);
            trainer.setGender(Gender.getGenderByValue(trainerDto.getGender()));
            trainerService.save(trainer);

            return "redirect:/admin/trainer-management";
        } else {
            return "form-add-trainer";
        }
    }

    @GetMapping("/trainer/{trainerId}")
    public String showTrainerDetail(ModelMap modelMap, @PathVariable Integer trainerId){
        Trainer trainer = trainerService.findById(trainerId);
        modelMap.put("trainer", trainer);
        return "detail-trainer";
    }

    //view list feed back
    @GetMapping("/trainer/{trainerId}/feed-back")
    public String showTrainerFeedBack(Model model, @PathVariable("trainerId")Integer trainerId) {
        Trainer trainer = trainerService.findById(trainerId);
        Class class_1 = classService.findClassByTrainer(trainer);
        List<ClassCourse> classCourseList = classCourseService.findAllByClass(class_1);
        List<Course> courseList = new ArrayList<>();
        Course course = null;
        for (ClassCourse classCourse : classCourseList) {
            course = classCourse.getCourse();
            courseList.add(course);
        }

        model.addAttribute("trainer", trainer);
        model.addAttribute("courseList", courseList);
        return "trainer-feedback";
    }

    //view detail feedback by subject
    @GetMapping("/trainer/{trainerId}/feed-back/{courseId}")
    public String showFeedbackSubject(@PathVariable("trainerId")Integer trainerId,
                                      @PathVariable("courseId")Integer courseId,
                                      Model model) {
        Trainer trainer = trainerService.findById(trainerId);
        Course course = courseService.findCourseById(courseId);
        List<CourseTrainee> courseTraineeList = courseTraineeService.findAllByCourse(course);
        List<FeedBack> feedBackList = new ArrayList<>();
        FeedBack feedBack = null;
        for (CourseTrainee courseTrainee : courseTraineeList) {
            feedBack = courseTrainee.getFeedBack();
            feedBackList.add(feedBack);
        }
        Iterator<FeedBack> iterator = feedBackList.iterator();
        while (iterator.hasNext()) {
            FeedBack feedBack1 = iterator.next();
            if (feedBack1 == null) {
                iterator.remove();
            }
        }
        model.addAttribute("trainer", trainer);
        model.addAttribute("course", course);
        model.addAttribute("feedBackList", feedBackList);
        return "trainer-feedback-subject";
    }

    //view detail feedback of trainee
    @GetMapping("/trainer/{trainerId}/feed-back/{courseId}/detail/{feedBackId}")
    public String showFeedBackOfTrainee(
            @PathVariable("trainerId")Integer trainerId,
            @PathVariable("courseId")Integer courseId,
            @PathVariable("feedBackId")Integer feedBackId,
            Model model
    ) {
        Trainer trainer = trainerService.findById(trainerId);
        Course course = courseService.findCourseById(courseId);
        FeedBack feedBack = feedBackService.findFeedBackById(feedBackId);

        List<QuestionFeedBack> questionFeedBackList = questionFeedBackService.findAllByFeedBack(feedBack);

        Iterator<QuestionFeedBack> iterator = questionFeedBackList.iterator();
        while (iterator.hasNext()) {
            QuestionFeedBack questionFeedBack = iterator.next();
            if (questionFeedBack.getGroupOfQuestion().name().equals("QUESTION_1")) {
                iterator.remove();
            }
            if (questionFeedBack.getGroupOfQuestion().name().equals("QUESTION_2")) {
                iterator.remove();
            }
            if (questionFeedBack.getGroupOfQuestion().name().equals("QUESTION_3")) {
                iterator.remove();
            }
            if (questionFeedBack.getGroupOfQuestion().name().equals("QUESTION_4")) {
                iterator.remove();
            }
            if (questionFeedBack.getGroupOfQuestion().name().equals("QUESTION_5")) {
                iterator.remove();
            }
            if (questionFeedBack.getGroupOfQuestion().name().equals("QUESTION_6")) {
                iterator.remove();
            }
            if (questionFeedBack.getGroupOfQuestion().name().equals("QUESTION_7")) {
                iterator.remove();
            }
            if (questionFeedBack.getGroupOfQuestion().name().equals("QUESTION_13")) {
                iterator.remove();
            }
            if (questionFeedBack.getGroupOfQuestion().name().equals("QUESTION_14")) {
                iterator.remove();
            }
            if (questionFeedBack.getGroupOfQuestion().name().equals("QUESTION_15")) {
                iterator.remove();
            }
        }

        System.out.println(questionFeedBackList.size());

        model.addAttribute("trainer", trainer);
        model.addAttribute("course", course);
        model.addAttribute("questionFeedBackList", questionFeedBackList);
        return "trainer-feedback-detail";
    }
}
