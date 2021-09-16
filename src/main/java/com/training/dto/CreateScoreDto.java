package com.training.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateScoreDto {

    private Double quizScore;

    private Double assignmentScore;

    private Double finalExamScore;

    private Integer traineeCourseId;

}
