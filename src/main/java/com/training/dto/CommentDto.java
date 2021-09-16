package com.training.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    private Integer id;
    private Date dateComment;
    private String content;
    private TraineeDto trainee;
    private TrainerDto trainer;

}
