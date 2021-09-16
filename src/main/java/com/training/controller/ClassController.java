package com.training.controller;


import com.training.dto.*;
import com.training.entities.Class;
import com.training.entities.ClassCourse;
import com.training.entities.InterviewResult;
import com.training.entities.Trainee;
import com.training.entities.enumeration.InterviewStatus;
import com.training.entities.enumeration.StatusOfTrainee;
import com.training.service.ClassCourseService;
import com.training.service.ClassService;
import com.training.service.CourseService;
import com.training.service.TrainerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class ClassController {
    @Autowired
    private ClassService classService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ClassCourseService classCourseService;

    @Autowired
    private ModelMapper modelMapper;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
//        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
//        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }


    /**
     * author longnb8
     * @param modelMap
     * @return
     */
    @GetMapping("/class-management")
    @PreAuthorize("hasRole('TRAINER') or hasRole('ADMIN')")
    public String uiClassManagement(ModelMap modelMap,
                                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNumber,
                                    @RequestParam(value = "limit", required = false, defaultValue = "5") Integer pageSize,
                                    Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        //System.out.println("conbeo gu ===" + userPrinciple.getAuthorities().toString());
        Page<Class> classPages = null;
        if("ROLE_TRAINER".equals(userPrinciple.getAuthorities().toArray()[0].toString())) {
            classPages = classService.findAllByTrainer(pageNumber-1, pageSize, userPrinciple.getId());
        } else {
            classPages = classService.findAll(pageNumber-1, pageSize);
        }

        List<Class> classes = classPages.getContent();
        modelMap.put("classList", classes);
        modelMap.put("page", pageNumber);
        modelMap.put("pageTotal", classPages.getTotalPages());
        return "class_management";
    }

    /**
     * author longnb8
     * @param modelMap
     * @return
     */
    @GetMapping("/add-class")
    @PreAuthorize("hasRole('ADMIN')")
    public String uiAddClass(ModelMap modelMap) {
        modelMap.put("trainers", trainerService.findAll());
        modelMap.put("courses", courseService.findAll());
        modelMap.put("class", new ClassDto());
        return "form_add_class";
    }

    /**
     * author longnb8
     * @param modelMap
     * @param classDto
     * @param id
     * @return
     */
    @PostMapping("/add-class")
    @PreAuthorize("hasRole('ADMIN')")
    public String addClass(ModelMap modelMap,
                           @ModelAttribute("class") ClassDto classDto,
                           @RequestParam(value = "trainer", required = false) Integer id) {
        if(classService.checkClassNameExist(classDto.getName())) {
            modelMap.put("error", "Class Name is existed!");
            modelMap.put("trainers", trainerService.findAll());
            modelMap.put("class", classDto);
            modelMap.put("courses", courseService.findAll()  );
            return "form_add_class";
        }

        Class classs = modelMapper.map(classDto, Class.class);
        List<ClassCourse> classCourses = classs.getClassCourses();
        classCourses.forEach(x -> {
            x.setClasss(classs);
            x.setId(null);
        });

        classs.setClassCourses(classCourses);
        if(id != null) {
            classs.setTrainer(trainerService.findById(id));
        }
        classService.save(classs);
        classCourseService.saveAll(classCourses);
        return "redirect:/admin/class-management";
    }

    /**
     * author longnb8
     * @param response
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping("/class/export/excel")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportClassToExcel(HttpServletResponse response) throws IOException, InterruptedException {
        response.setContentType("application/octet-stream");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=class_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        classService.export(response);
        // response.sendRedirect("/admin/class-management");
    }

    /**
     * author longnb8
     * @param file
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @PostMapping("/import/class")
    @PreAuthorize("hasRole('ADMIN')")
    public String importFileExcel(@RequestParam("file")MultipartFile file) throws IOException, ParseException {
        classService.insertDateFromExcel(file);
        return "redirect:/admin/class-management";
    }


    /**
     * author HuyLQ21
     *
     * @param modelMap, class_Id
     * @return detail of a class
     */
    @GetMapping("/class-management/{classId}")
    @PreAuthorize("hasRole('TRAINER') or hasRole('ADMIN')")
    public String getDetailClass(ModelMap modelMap, @PathVariable Integer classId) throws Exception {
        if(null==classService.findById(classId)){
            throw new Exception("Class have id: "+classId+" not exists!");
        }
        Class classs = classService.findById(classId);
        modelMap.put("class", classs);

        InterviewResult interviewResult;
        List<InterviewResult> interviewResultList;

        Integer countPassed = 0;

        Integer countDropOut = 0;

        Integer countFailed = 0;

        for (Trainee trainee : classs.getTrainees()) {

            interviewResultList = trainee.getInterviewResults();

            if(interviewResultList.size() == 0){
                continue;
            } else {
            List<InterviewResult> sortedList = interviewResultList
                    .stream()
                    .sorted(Comparator.comparing(InterviewResult::getInterviewDate).reversed())
                    .collect(Collectors.toList());
            interviewResult = sortedList.get(0);
                System.out.println(interviewResult);
            }

            if (trainee.getStatus().equals(StatusOfTrainee.StillLearning)) {
                if (null != interviewResult.getInterviewStatus()) {

                    if (interviewResult.getInterviewStatus().equals(InterviewStatus.Failed)) {
                        countFailed += 1;
                    } else if (interviewResult.getInterviewStatus().equals(InterviewStatus.Passed)) {
                        countPassed += 1;
                    }
                }
            } else if (trainee.getStatus().equals(StatusOfTrainee.Dropout)) {
                countDropOut += 1;
            }

        }

        modelMap.put("countPassed", countPassed);
        modelMap.put("countFailed", countFailed);
        modelMap.put("countDropout", countDropOut);
        modelMap.put("createIssueDto", new CreateIssueDto());
        modelMap.put("updateIssueDto", new UpdateIssueDto());
        modelMap.put("updateSolutionDto", new UpdateIssueDto());
        modelMap.put("createSolutionDto", new CreateSolutionDto());

        return "detail_class";
    }


}
