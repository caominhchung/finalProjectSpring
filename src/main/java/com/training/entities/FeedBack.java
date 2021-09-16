package com.training.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "FeedBack")
@Getter
@Setter
@NoArgsConstructor
public class FeedBack implements Serializable {

    @Id
    @Column(name = "FeedBack_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ConsultDate")
    private Date consultDate;

    @Column(name = "FeedBackScore")
    private Double feedBackScore;

    @JoinColumn(name = "Course_trainee_id", unique = true)
    @OneToOne
    private CourseTrainee trainingObjective;

    @OneToMany(mappedBy = "feedBack")
    private List<QuestionFeedBack> questionFeedBacks = new ArrayList<>();

}
