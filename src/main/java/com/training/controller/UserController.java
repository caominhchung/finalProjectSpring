package com.training.controller;

import com.training.entities.*;
import com.training.entities.enumeration.GroupOfQuestion;
import com.training.service.*;
import com.training.service.impl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
//@SessionAttributes("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private FeedBackService feedBackService;

    @Autowired
    private QuestionFeedBackService questionFeedBackService;

    @Autowired
    private CourseTraineeService courseTraineeService;

    /**
     * Method showChangePasswordForm is used to show form to change password
     *
     * @author ChungCM
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/change-password/{userId}")
    public String showChangePasswordForm(@PathVariable("userId") Integer id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "change-password";
    }



    /**
     * Method submitChangePasswordForm is used to change password for user and save into database
     *
     * @author ChungCM
     * @param model
     * @param redirectAttributes
     * @param newPassword
     * @param account
     * @param password
     * @return
     */
    @PostMapping("/change-password")
    public String submitChangePasswordForm(
            @RequestParam("newPassword") String newPassword,
            @RequestParam("account") String account,
            @RequestParam("password") String password,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        User user1 = userService.findUserByAccountAndPassword(account, password);
        if (user1 == null) {
            model.addAttribute("message", "UserName or Password is not correct!");
            model.addAttribute("user", userService.findUserByAccount(account));
            return "change-password";
        } else {
            user1.setPassword(encoder.encode(newPassword));
            user1.setActive(true);
            userService.changePassword(user1);
            redirectAttributes.addFlashAttribute("user", user1);
            return "redirect:/login";
        }
    }




    /**
     * Method showFormFeedBack is used to show form feedback for User
     *
     * @author ChungCM
     * @param model
     * @return
     */
    @GetMapping("/user/feed-back")
    public String showFormFeedBack(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        User user = userService.findUserByAccount(userName);
        Trainee trainee = (Trainee) user;
        List<Course> courseList = courseService.findAll();
        model.addAttribute("trainee", trainee);
        model.addAttribute("courseList", courseList);
        return "form-feedback";
    }

    /**
     * Method submitFeedBack is used to submit form feedback for User
     *
     * @author ChungCM
     * @param courseId
     * @param ques1, ques2, ques3, ques4, ques5, ques6, ques7
     * @param ques8, ques9, ques10, ques11, ques12, ques13, ques14, ques15
     * @throws ParseException
     * @return
     */
    @PostMapping("/user/feed-back")
    public String submitFeedBack
                                (
                                 @RequestParam("courseId")String courseId,
                                 @RequestParam("ques1")String ques1,
                                 @RequestParam("ques2")String ques2,
                                 @RequestParam("ques3")String ques3,
                                 @RequestParam("ques4")String ques4,
                                 @RequestParam("ques5")String ques5,
                                 @RequestParam("ques6")String ques6,
                                 @RequestParam("ques7")String ques7,
                                 @RequestParam("ques8")String ques8,
                                 @RequestParam("ques9")String ques9,
                                 @RequestParam("ques10")String ques10,
                                 @RequestParam("ques11")String ques11,
                                 @RequestParam("ques12")String ques12,
                                 @RequestParam("ques13")String ques13,
                                 @RequestParam("ques14")String ques14,
                                 @RequestParam("ques15")String ques15) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = simpleDateFormat.format(new Date());
        Double ques_3 = Double.parseDouble(ques3);
        Double ques_4 = Double.parseDouble(ques4);
        Double ques_5 = Double.parseDouble(ques5);
        Double ques_6 = Double.parseDouble(ques6);
        Double ques_7 = Double.parseDouble(ques7);
        Double ques_8 = Double.parseDouble(ques8);
        Double ques_9 = Double.parseDouble(ques9);
        Double ques_10 = Double.parseDouble(ques10);
        Double ques_11 = Double.parseDouble(ques11);
        Double ques_13 = Double.parseDouble(ques13);
        Double ques_14 = Double.parseDouble(ques14);
        Double score2 = (ques_3 + ques_4 + ques_5 + ques_6 + ques_7 + ques_8 + ques_9 + ques_10 + ques_11 + ques_13 + ques_14) / 11;
        Double score = (double) Math.round(score2 * 10) / 10;
        Date consultDate = simpleDateFormat.parse(date);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = userDetails.getUsername();
        User user = userService.findUserByAccount(userName);
        Trainee trainee = (Trainee) user;

        Integer course_id = Integer.parseInt(courseId);
        Course course = courseService.findCourseById(course_id);

        CourseTrainee courseTrainee = courseTraineeService.findCourseTraineeByCourseAndTrainee(course, trainee);

        FeedBack feedBack = new FeedBack();
        feedBack.setConsultDate(consultDate);
        feedBack.setFeedBackScore(score);
        feedBack.setTrainingObjective(courseTrainee);
        feedBackService.save(feedBack);

        QuestionFeedBack questionFeedBack1 = new QuestionFeedBack();
        questionFeedBack1.setGroupOfQuestion(GroupOfQuestion.QUESTION_1);
        questionFeedBack1.setContent(ques1);
        questionFeedBack1.setFeedBack(feedBack);
        questionFeedBackService.save(questionFeedBack1);

        QuestionFeedBack questionFeedBack2 = new QuestionFeedBack();
        questionFeedBack2.setGroupOfQuestion(GroupOfQuestion.QUESTION_2);
        questionFeedBack2.setContent(ques2);
        questionFeedBack2.setFeedBack(feedBack);
        questionFeedBackService.save(questionFeedBack2);

        QuestionFeedBack questionFeedBack3 = new QuestionFeedBack();
        questionFeedBack3.setGroupOfQuestion(GroupOfQuestion.QUESTION_3);
        questionFeedBack3.setContent(ques3);
        questionFeedBack3.setFeedBack(feedBack);
        questionFeedBackService.save(questionFeedBack3);

        QuestionFeedBack questionFeedBack4 = new QuestionFeedBack();
        questionFeedBack4.setGroupOfQuestion(GroupOfQuestion.QUESTION_4);
        questionFeedBack4.setContent(ques4);
        questionFeedBack4.setFeedBack(feedBack);
        questionFeedBackService.save(questionFeedBack4);

        QuestionFeedBack questionFeedBack5 = new QuestionFeedBack();
        questionFeedBack5.setGroupOfQuestion(GroupOfQuestion.QUESTION_5);
        questionFeedBack5.setContent(ques5);
        questionFeedBack5.setFeedBack(feedBack);
        questionFeedBackService.save(questionFeedBack5);

        QuestionFeedBack questionFeedBack6 = new QuestionFeedBack();
        questionFeedBack6.setGroupOfQuestion(GroupOfQuestion.QUESTION_6);
        questionFeedBack6.setContent(ques6);
        questionFeedBack6.setFeedBack(feedBack);
        questionFeedBackService.save(questionFeedBack6);

        QuestionFeedBack questionFeedBack7 = new QuestionFeedBack();
        questionFeedBack7.setGroupOfQuestion(GroupOfQuestion.QUESTION_7);
        questionFeedBack7.setContent(ques7);
        questionFeedBack7.setFeedBack(feedBack);
        questionFeedBackService.save(questionFeedBack7);

        QuestionFeedBack questionFeedBack8 = new QuestionFeedBack();
        questionFeedBack8.setGroupOfQuestion(GroupOfQuestion.QUESTION_8);
        questionFeedBack8.setContent(ques8);
        questionFeedBack8.setFeedBack(feedBack);
        questionFeedBackService.save(questionFeedBack8);

        QuestionFeedBack questionFeedBack9 = new QuestionFeedBack();
        questionFeedBack9.setGroupOfQuestion(GroupOfQuestion.QUESTION_9);
        questionFeedBack9.setContent(ques9);
        questionFeedBack9.setFeedBack(feedBack);
        questionFeedBackService.save(questionFeedBack9);

        QuestionFeedBack questionFeedBack10 = new QuestionFeedBack();
        questionFeedBack10.setGroupOfQuestion(GroupOfQuestion.QUESTION_10);
        questionFeedBack10.setContent(ques10);
        questionFeedBack10.setFeedBack(feedBack);
        questionFeedBackService.save(questionFeedBack10);

        QuestionFeedBack questionFeedBack11 = new QuestionFeedBack();
        questionFeedBack11.setGroupOfQuestion(GroupOfQuestion.QUESTION_11);
        questionFeedBack11.setContent(ques11);
        questionFeedBack11.setFeedBack(feedBack);
        questionFeedBackService.save(questionFeedBack6);

        QuestionFeedBack questionFeedBack12 = new QuestionFeedBack();
        questionFeedBack12.setGroupOfQuestion(GroupOfQuestion.QUESTION_12);
        questionFeedBack12.setContent(ques12);
        questionFeedBack12.setFeedBack(feedBack);
        questionFeedBackService.save(questionFeedBack12);

        QuestionFeedBack questionFeedBack13 = new QuestionFeedBack();
        questionFeedBack13.setGroupOfQuestion(GroupOfQuestion.QUESTION_13);
        questionFeedBack13.setContent(ques13);
        questionFeedBack13.setFeedBack(feedBack);
        questionFeedBackService.save(questionFeedBack13);

        QuestionFeedBack questionFeedBack14 = new QuestionFeedBack();
        questionFeedBack14.setGroupOfQuestion(GroupOfQuestion.QUESTION_14);
        questionFeedBack14.setContent(ques14);
        questionFeedBack14.setFeedBack(feedBack);
        questionFeedBackService.save(questionFeedBack14);

        QuestionFeedBack questionFeedBack15 = new QuestionFeedBack();
        questionFeedBack15.setGroupOfQuestion(GroupOfQuestion.QUESTION_15);
        questionFeedBack15.setContent(ques15);
        questionFeedBack15.setFeedBack(feedBack);
        questionFeedBackService.save(questionFeedBack15);


        return "redirect:/dashboard";

    }



}
