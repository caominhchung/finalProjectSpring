package com.training.controller.API;

import com.training.dto.CommentDto;
import com.training.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainer")
public class CommentControllerApi {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add-comment")
    public CommentDto addComment(@RequestBody CommentDto commentDto){
        commentService.save(commentDto);
        return commentDto;
    }

    @GetMapping("/show-comment/{id}")
    public List<CommentDto> getList(@PathVariable Integer id) {
        return commentService.findCommentByTraineeIdAndTrainerId(id, 1);
    }
}
