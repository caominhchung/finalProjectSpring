package com.training.entities;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Internship")
@PrimaryKeyJoinColumn(name = "Internship_id")
@NoArgsConstructor
public class Internship extends Trainee implements Serializable {
}
