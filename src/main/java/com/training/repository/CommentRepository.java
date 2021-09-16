package com.training.repository;

import com.training.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
     List<Comment> findCommentByTraineeId(Integer traineeId);

     List<Comment> findCommentByTraineeIdAndTrainerId(Integer traineeId, Integer trainerId);
}
