package com.training.entities;

import com.training.entities.enumeration.ClassTypeName;
import com.training.entities.enumeration.StatusOfClass;
import com.training.entities.enumeration.converter.StatusOfClassConverter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Class")
@Getter
@Setter
@NoArgsConstructor
public class Class implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Class_Id")
    private Integer id;

    @Column(name = "Name", unique = true, nullable = false)
    private String name;

    @Column(name = "StartDate")
    private Date startDate;

    @Column(name = "EndDate")
    private Date endDate;

    @Column(name = "Note")
    private String note;

    @Column(name = "Status")
    @Convert(converter = StatusOfClassConverter.class)
    private StatusOfClass statusOfClass;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ClassTypeName type;

    @Column(name = "planCount")
    private Integer planCount;

    @OneToMany(mappedBy = "classOfTrainee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Trainee> trainees= new ArrayList<>();

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "Trainer_id")
    private Trainer trainer;

    @OneToMany(mappedBy = "classs" , cascade = CascadeType.ALL)
    private List<Issue> issues = new ArrayList<>();

    @OneToMany(mappedBy = "classs", cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH
    })
    private List<ClassCourse> classCourses = new ArrayList<>();


}
