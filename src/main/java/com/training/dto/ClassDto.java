package com.training.dto;

import com.training.entities.ClassCourse;
import com.training.entities.Course;
import com.training.entities.Trainee;
import com.training.entities.Trainer;
import com.training.entities.enumeration.ClassTypeName;
import com.training.entities.enumeration.StatusOfClass;
import com.training.entities.enumeration.converter.StatusOfClassConverter;
import lombok.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassDto {
    private Integer id;
    private String name;
    private Date startDate;
    private Date endDate;
    private String note;
    private String statusOfClass;
    private String type;
    private Integer planCount;
    private List<TraineeDto> trainees= new ArrayList<>();
    private Trainer trainer;
    private List<Course> classCourses = new ArrayList<>();

}
