package com.training.controller;

import com.training.dto.MistakeDto;
import com.training.entities.Mistake;
import com.training.entities.Trainee;
import com.training.service.MistakeService;
import com.training.service.TraineeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author tungns14
 */
@Controller
@RequestMapping("/admin")
public class MistakeController {

    @Autowired
    private MistakeService mistakeService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TraineeService traineeService;

    @GetMapping("/trainee/{traineeId}/mistake")
    public String getTraineeMistakes(@PathVariable("traineeId")Integer traineeId, Model model){

        List<Mistake> mistakes = mistakeService.findAllByTraineeId(traineeId);
        model.addAttribute("mistakeList", mistakes);
        return "mistake-management";
    }

    @GetMapping("/trainee/{traineeId}/mistake/add")
    public String getAddMistake(@PathVariable("traineeId")Integer traineeId, Model model){
        MistakeDto mistakeDto = new MistakeDto();
        model.addAttribute("mistakeDto", mistakeDto);
        model.addAttribute("traineeId", traineeId);
        return "form-add-mistake";
    }

    @PostMapping("/trainee/mistake/add")
    public String submitAddMistake(@Valid @ModelAttribute("mistakeDto") MistakeDto mistakeDto, BindingResult bindingResult,
                                   Model model, @RequestParam("traineeId") Integer traineeId){
        Trainee trainee = traineeService.findById(traineeId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("mistakeDto", mistakeDto);
            model.addAttribute("traineeId", traineeId);
            return "form-add-mistake";
        }
        Mistake mistake = modelMapper.map(mistakeDto, Mistake.class);
        mistake.setTrainee(trainee);
        mistakeService.save(mistake);

//        List<Mistake> mistakes = mistakeService.findAllByTraineeId(traineeId);
//        model.addAttribute("mistakeList", mistakes);
//        model.addAttribute("traineeId", traineeId);
        return "redirect:" + "/admin/trainee/" + traineeId + "/mistake";
    }
}
