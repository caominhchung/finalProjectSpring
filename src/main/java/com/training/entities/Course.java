package com.training.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Course")
@Getter
@Setter
@NoArgsConstructor
@Component
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Course_id")
    private Integer id;

    @Column(name = "Name", unique = true, nullable = false)
    private String name;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "Duration", nullable = false)
    private Double duration;

    @Column(name = "Lecture")
    private String lecture;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CourseTrainee> courseTrainees = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<ClassCourse> classCourses = new ArrayList<>();


}
