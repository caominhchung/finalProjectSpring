package com.training.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CourseTrainee", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"Course_id", "Trainee_id"})
})
@Getter
@Setter
@NoArgsConstructor
public class CourseTrainee implements Serializable {

    @Id
    @Column(name = "Course_trainee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Trainee_id")
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "Course_id")
    private Course course;

    @OneToOne(mappedBy = "trainingObjective")
    private FeedBack feedBack;

    @OneToMany(mappedBy = "courseTrainee", cascade = CascadeType.ALL)
    List<Score> scores = new ArrayList<>();
}
