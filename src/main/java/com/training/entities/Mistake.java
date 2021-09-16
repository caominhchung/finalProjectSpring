package com.training.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author tungns14
 */
@Entity
@Table(name = "Mistake")
@Getter
@Setter
@NoArgsConstructor
public class Mistake implements Serializable {
    @Id
    @Column(name = "Mistake_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name ="Mistake_name")
    private String name;

    @Column(name = "Mistake_content")
    private String content;

    @Column(name = "Note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "Trainee_id")
    private Trainee trainee;

}
