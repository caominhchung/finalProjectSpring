package com.training.service.impl;

import com.training.dto.CommentDto;
import com.training.entities.Comment;
import com.training.repository.CommentRepository;
import com.training.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * author: longnb8
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<CommentDto> findCommentByTraineeId(Integer traineeId) {
        return commentRepository.findCommentByTraineeId(traineeId)
                .stream()
                .map(e -> modelMapper.map(e, CommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<CommentDto> findCommentByTraineeIdAndTrainerId(Integer traineeId, Integer trainerId){
        return commentRepository.findCommentByTraineeIdAndTrainerId(traineeId, trainerId)
                .stream()
                .map(e -> modelMapper.map(e, CommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void save(CommentDto commentDto) {
        Comment comment =  modelMapper.map(commentDto, Comment.class);
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Comment comment){
        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public Comment findCommentById(Integer id){
        return commentRepository.findById(id).get();
    }

}
