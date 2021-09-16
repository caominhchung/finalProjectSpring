package com.training.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.training.entities.enumeration.TypeAttendance;
import com.training.entities.enumeration.converter.TypeAttendanceConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Attendance")
@Getter
@Setter
@NoArgsConstructor
public class Attendance implements Serializable {

    @Id
    @Column(name = "Attendance_Id")
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Color")
    private String color;

    @Column(name = "Type")
    @JsonIgnore
    @Convert(converter = TypeAttendanceConverter.class)
    private TypeAttendance type;

    @Column(name = "Start")
    private String start;

    @Column(name = "Title")
    @JsonIgnore
    private String title;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "User_id")
    private User user;


}
