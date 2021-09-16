package com.training.controller;

import com.training.dto.CommentDto;
import com.training.dto.CreateTraineeDto;
import com.training.dto.UserPrinciple;
import com.training.entities.Attendance;
import com.training.entities.*;
import com.training.entities.Class;
import com.training.entities.Mistake;
import com.training.entities.Trainee;
import com.training.entities.enumeration.Gender;
import com.training.entities.enumeration.Role;
import com.training.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TraineeController {
    @Autowired
    private TraineeService traineeService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ClassService classService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CourseTraineeService courseTraineeService;

    @Autowired
    private AttendanceService attendanceService;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
//        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
//        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/trainee-management")
    public String traineeManagement(ModelMap modelMap) {
        List<Trainee> trainees = traineeService.findAll();
        modelMap.put("traineeList", trainees);
        return "trainee-management";
    }

    @GetMapping("/add-trainee")
    public String showFormAddTrainee(ModelMap map){
        map.put("createTraineeDto", new CreateTraineeDto());
        List<Class> classes = classService.findAll();
        map.put("classList", classes);
        return "add-trainee";
    }

    @PostMapping("/trainee-management/add-trainee")
    public String addTraineeForClass(@Valid @ModelAttribute("createTraineeDto") CreateTraineeDto dto, BindingResult bindingResult, ModelMap map){

        boolean check = true;
        String account = dto.getAccount();
        String email = dto.getEmail();
        String facebook = dto.getFacebook();
        String phone = dto.getTelNumber();
        if(null!=userService.findUserByAccount(account)){
            map.put("errorAccount","Account existed");
            check = false;
        }
        if(null!=userService.findUserByTelPhone(phone)){
            map.put("errorPhone","Phone number existed");
            check = false;
        }

        if(null!=traineeService.getTraineeByEmail(email)){
            map.put("errorEmail","Email existed");
            check = false;
        }

        if(null!=traineeService.getTraineeByFacebook(facebook)){
            map.put("errorFacebook","facebook existed");
            check = false;
        }

        if(bindingResult.hasErrors()){
            return "add-trainee";
        }

        if(check){
            System.out.println("class id = " + dto.getClassOfTrainee());
            Trainee trainee = modelMapper.map(dto, Trainee.class);
            System.out.println("trainee id = " + trainee.getId());
            Class classOfTrainee = classService.findById(dto.getClassOfTrainee());
            trainee.setClassOfTrainee(classOfTrainee);
            trainee.setRole(Role.ROLE_TRAINEE);
            trainee.setGender(Gender.getGenderByValue(dto.getGender()));
            traineeService.createTrainee(trainee);

            List<Course> courses = new ArrayList<>();
            for(ClassCourse classCourse:trainee.getClassOfTrainee().getClassCourses()){
                courses.add(classCourse.getCourse());
            }
            for(Course course: courses){
                CourseTrainee courseTrainee = new CourseTrainee();
                courseTrainee.setTrainee(trainee);
                courseTrainee.setCourse(course);
                courseTraineeService.createCourseTrainee(courseTrainee);
            }

            return "redirect:/admin/trainee-management";
        }else{
            List<Class> classes = classService.findAll();
            map.put("classList", classes);
            return "add-trainee";
        }
    }

    /**
     * author HuyLQ21
     *
     * @param map, class_Id
     * @return form create trainee
     */
    @GetMapping("/class-management/{classId}/add-trainee")
    public String addTraineeForClassForm(@PathVariable Integer classId, ModelMap map) throws Exception {
        if(null==classService.findById(classId)){
            throw new Exception("Class have id: "+classId+" not exists!");
        }
        map.put("createTraineeDto", new CreateTraineeDto());
        return "form_add_trainee";
    }

    /**
     * author HuyLQ21
     *
     * @param dto, class_Id, map
     * @todo: create a new trainee
     */
    @PostMapping("/class-management/{classId}/add-trainee")
    public String addTraineeForClass(@Valid @ModelAttribute("createTraineeDto") CreateTraineeDto dto, BindingResult bindingResult, @PathVariable Integer classId, ModelMap map) throws Exception {
        if(null==classService.findById(classId)){
            throw new Exception("Class have id: "+classId+" not exists!");
        }
        boolean check = true;
        String account = dto.getAccount();
        String email = dto.getEmail();
        String facebook = dto.getFacebook();
        String phone = dto.getTelNumber();
        if (null != userService.findUserByAccount(account)) {
            map.put("errorAccount", "Account existed");
            check = false;
        }
        if (null != userService.findUserByTelPhone(phone)) {
            map.put("errorPhone", "Phone number existed");
            check = false;
        }

        if (null != traineeService.getTraineeByEmail(email)) {
            map.put("errorEmail", "Email existed");
            check = false;
        }

        if (null != traineeService.getTraineeByFacebook(facebook)) {
            map.put("errorFacebook", "facebook existed");
            check = false;
        }

        if (bindingResult.hasErrors()) {
            return "form_add_trainee";
        }

        if (check) {
            Trainee trainee = modelMapper.map(dto, Trainee.class);
            Class classOfTrainee = classService.findById(classId);
            trainee.setClassOfTrainee(classOfTrainee);
            trainee.setPassword(trainee.getPassword());
            trainee.setRole(Role.ROLE_TRAINEE);
            trainee.setGender(Gender.getGenderByValue(dto.getGender()));
            traineeService.createTrainee(trainee);
            map.put("createClassDto", new CreateTraineeDto());
            List<Course> courses = new ArrayList<>();
            for(ClassCourse classCourse:trainee.getClassOfTrainee().getClassCourses()){
                courses.add(classCourse.getCourse());
            }
            for(Course course: courses){
                CourseTrainee courseTrainee = new CourseTrainee();
                courseTrainee.setTrainee(trainee);
                courseTrainee.setCourse(course);
                courseTraineeService.createCourseTrainee(courseTrainee);
            }
            return "redirect:/admin/class-management/{classId}";
        } else {
            return "form_add_trainee";
        }
    }


    @PostMapping("/import/trainee")
    public String importFileExcel(@RequestParam("file") MultipartFile file) throws IOException, ParseException {
//        System.out.println("import trainee controller");
        traineeService.insertDataFromExcel(file);
        return "redirect:/admin/trainee-management";
    }

    @GetMapping("/trainee/{traineeId}")
    public String showDetailTrainee(ModelMap modelMap, @PathVariable Integer traineeId) {
        Trainee trainee = traineeService.findById(traineeId);
        List<CommentDto> commentList = commentService.findCommentByTraineeId(traineeId);
        List<Attendance> attendances = attendanceService.findAttendancesByUserId(traineeId);
        modelMap.put("commentList", commentList);
        List<Mistake> mistakeList = trainee.getMistakes();
        modelMap.put("mistakeList", mistakeList);
//        System.out.println("comment list = " + commentList.size());
//        System.out.println("mistake list = " + mistakeList.size());
        modelMap.put("trainee", trainee);
        modelMap.put("attendanceList", attendances);
        return "detail-trainee";
    }

    @GetMapping("/trainee-management/checkin")
    public String showTraineeCheckIn(ModelMap modelMap){
        return "checkin";
    }

    @PostMapping("/trainee-management/checkedin")
    public String traineeCheckedIn(ModelMap modelMap, Authentication authentication){
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Trainee trainee = traineeService.findById(userPrinciple.getId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Attendance attendance = new Attendance();
        attendance.setStart(formatter.format(date));
        attendance.setUser(trainee);
        attendance.setColor("green");
        attendanceService.save(attendance);
        return "redirect:/dashboard";
    }

//    @GetMapping("/api/attendance")
//    public String test(){
//        System.out.println(attendanceService.findAttendancesByUserId(3).size());
//        Gson gson = new Gson();
//        return gson.toJson(attendanceService.findAttendancesByUserId(3));
//    }
}
