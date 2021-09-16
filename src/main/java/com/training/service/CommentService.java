package com.training.service;

import com.training.dto.CommentDto;
import com.training.entities.Comment;

import java.util.List;
import java.util.stream.Collectors;

public interface CommentService {
    List<CommentDto> findCommentByTraineeId(Integer traineeId);

    List<CommentDto> findCommentByTraineeIdAndTrainerId(Integer traineeId, Integer trainerId);

    public void save(Comment comment);

    void save(CommentDto commentDto);

    public void deleteComment(Comment comment);

    public Comment findCommentById(Integer id);
}
