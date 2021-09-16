package com.training.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Fresher")
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "Fresher_id")
@NoArgsConstructor
public class Fresher extends Trainee implements Serializable {

    @Column(name = "UniversityGraduationDate")
    private Date universityGraduationDate;

    @Column(name = "FullTimeWorkingAvailable")
    private Date fullTimeWorkingAvailable;


}
