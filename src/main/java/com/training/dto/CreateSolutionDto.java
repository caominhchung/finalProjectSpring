package com.training.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSolutionDto {

    private String title;
    private String content;
    private Integer issueId;
    private Integer classId;

}
