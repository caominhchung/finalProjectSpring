package com.training.entities;

import com.training.entities.enumeration.ScoreType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Score", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"Type", "Course_trainee_id"})
})
public class Score implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Score_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "Type")
    private ScoreType type;

    @Column(name = "Value")
    private Double value;

    @Column(name = "Note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "Course_trainee_id")
    private CourseTrainee courseTrainee;
}
