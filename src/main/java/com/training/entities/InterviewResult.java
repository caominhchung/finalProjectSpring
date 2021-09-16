package com.training.entities;

import com.training.entities.enumeration.InterviewStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author tungns14
 */
@Entity
@Table(name = "InterviewResult")
@Getter
@Setter
@NoArgsConstructor
public class InterviewResult implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Interview_Result_id")
    private Integer id;

    @Column(name = "InterviewDate")
    private Date interviewDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "InterviewStatus")
    private InterviewStatus interviewStatus;

    @Column(name = "Note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "Trainee_id")
    private Trainee trainee;

    @Override
    public String toString() {
        return "InterviewResult{" +
                "id=" + id +
                ", interviewDate=" + interviewDate +
                ", interviewStatus=" + interviewStatus +
                ", note='" + note + '\'' +
                '}';
    }
}
