package com.training.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ChungCM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private Integer id;
    private String name;
    private Double duration;
    private String lecture;
    private String description;

    public CourseDto(String name, Double duration, String lecture, String description) {
        this.name = name;
        this.duration = duration;
        this.lecture = lecture;
        this.description = description;
    }
}
