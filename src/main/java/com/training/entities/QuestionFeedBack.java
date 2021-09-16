package com.training.entities;

import com.training.entities.enumeration.GroupOfQuestion;
import com.training.entities.enumeration.converter.GroupOfQuestionConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "QuestionFeedBack")
@Getter
@Setter
@NoArgsConstructor
public class QuestionFeedBack implements Serializable {

    @Id
    @Column(name = "Question_feedback_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "GroupOfQuestion")
    @Convert(converter = GroupOfQuestionConverter.class)
    private GroupOfQuestion groupOfQuestion;

    @Column(name = "Topic")
    private String topic;

    @Column(name = "Content")
    private String content;

    @Column(name = "Note")
    private String note;

    @Column(name = "Score")
    private Double score;

    @ManyToOne
    @JoinColumn(name = "FeedBack_id")
    private FeedBack feedBack;
}
