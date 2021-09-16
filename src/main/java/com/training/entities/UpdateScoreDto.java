package com.training.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateScoreDto {

    private Double quizScore;
    private Integer quizScoreId;

    private Double assignmentScore;
    private Integer assignmentScoreId;

    private Double finalExamScore;
    private Integer finalExamScoreId;

    private Integer summaryScoreId;

}
