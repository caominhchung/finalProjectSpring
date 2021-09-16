package com.training.entities;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "admin")
@PrimaryKeyJoinColumn(name = "Admin_id")
@NoArgsConstructor
public class Admin extends User implements Serializable {


}
