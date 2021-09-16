package com.training.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Issue implements Serializable {

    @Id
    @Column(name = "issue_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @JoinColumn(name = "class_id")
    @ManyToOne
    private Class classs;

    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL)
    private List<Solution> solutions = new ArrayList<>();
}
