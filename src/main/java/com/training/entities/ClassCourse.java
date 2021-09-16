package com.training.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ClassCourse", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"Class_id", "Course_id"})
})
public class ClassCourse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "Class_Id")
    private Class classs;
}
