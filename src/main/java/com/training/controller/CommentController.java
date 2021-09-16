package com.training.controller;

import com.training.dto.CreateCommentDto;
import com.training.dto.UpdateCommentDto;
import com.training.entities.Comment;
import com.training.entities.Trainee;
import com.training.entities.Trainer;
import com.training.service.CommentService;
import com.training.service.TraineeService;
import com.training.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("trainer/comment")
@PreAuthorize("hasRole('TRAINER')")
public class CommentController {

    @Autowired
    private TraineeService traineeService;

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private CommentService commentService;

    @PostMapping("/createComment")
    public String createComment(@ModelAttribute CreateCommentDto createCommentDto, ModelMap modelMap) {
        Trainee trainee = traineeService.findById(createCommentDto.getTraineeId());
        Comment comment = new Comment();
        comment.setTrainee(traineeService.findById(createCommentDto.getTraineeId()));
        comment.setDateComment(new Date());
        comment.setContent(createCommentDto.getContent());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userNane = authentication.getName();
        Trainer trainerOfComment = trainerService.findTrainerByAccount(userNane);
        comment.setTrainer(trainerOfComment);
        commentService.save(comment);

        return "redirect:/trainer/score?traineeId="+trainee.getId()+"&createComment=true";
    }

    @GetMapping("/deleteComment")
    public String deleteComment(@RequestParam Integer idDelete, @RequestParam Integer traineeId){
        Comment comment = commentService.findCommentById(idDelete);
        commentService.deleteComment(comment);
        return "redirect:/trainer/score?traineeId="+traineeId+"&deleteComment=true";
    }

    @PostMapping("/updateComment")
    public String updateComment(@ModelAttribute UpdateCommentDto updateCommentDto){

        Comment comment = commentService.findCommentById(updateCommentDto.getCommentId());
        comment.setContent(updateCommentDto.getContent());
        commentService.save(comment);
        return "redirect:/trainer/score?traineeId="+updateCommentDto.getTraineeId()+"&updateComment=true";

    }
}
