package com.training.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Trainer")
@PrimaryKeyJoinColumn(name = "Trainer_id")
@Getter
@Setter
@NoArgsConstructor
public class Trainer extends User implements Serializable {

    @JsonIgnore
    @OneToMany(mappedBy = "trainer")
    private List<Class> classes= new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    private List<Comment> comments= new ArrayList<>();

}
