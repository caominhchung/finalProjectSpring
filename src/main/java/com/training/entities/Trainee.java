package com.training.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.training.entities.enumeration.InterviewStatus;
import com.training.entities.enumeration.StatusOfTrainee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Trainee")
@PrimaryKeyJoinColumn(name = "Trainee_id")
@Getter
@Setter
@NoArgsConstructor
public class Trainee extends User implements Serializable {

    @Column(name = "Branch")
    private String branch;

    @Column(name = "ParentDepartment")
    private String parentDepartment;

//    @Column(name = "InterviewDate")
//    private Date interviewDate;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "InterviewStatus")
//    private InterviewStatus interviewStatus;
//
    @Column(name = "Note")
    private String note;

    @Column(name = "University")
    private String university;

    @Column(name = "Faculty")
    private String faculty;

    @Column(name = "GPA")
    private Integer GPA;

    @Enumerated(EnumType.STRING)
    @Column(name = "StatusOfTrainee")
    private StatusOfTrainee status = StatusOfTrainee.StillLearning;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "Class_id")
    private Class classOfTrainee;

    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToOne(mappedBy = "trainee")
    private Certificate certificate;


    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL)
    private List<CourseTrainee> courseTrainees = new ArrayList<>();

    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL)
    private List<Mistake> mistakes = new ArrayList<>();

    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL)
    private List<InterviewResult> interviewResults = new ArrayList<>();
}
