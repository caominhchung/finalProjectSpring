package com.training.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Comment")
@Getter
@Setter
@NoArgsConstructor
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DateComment")
    private Date dateComment;

    @Column(name = "Content")
    private String content;
    
    @ManyToOne
    @JoinColumn(name = "Trainee_id")
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "Trainer_id")
    private Trainer trainer;

}
