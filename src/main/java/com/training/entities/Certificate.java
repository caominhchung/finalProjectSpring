package com.training.entities;


import com.training.entities.enumeration.Rate;
import com.training.entities.enumeration.converter.RateConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Certificatee")
@Getter
@Setter
@NoArgsConstructor
public class Certificate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Rate")
    @Convert(converter = RateConverter.class)
    private Rate rate;

    @Column(name = "CompletionLevel")
    private String completionLevel;

    @Column(name = "Provider")
    private String provider;

    @Column(name = "Groupp")
    private String group;

    @Column(name = "SubGroup")
    private String subGroup;

    @Column(name = "Name")
    private String name;

    @Column(name = "Code")
    private String code;

    @OneToOne
    @JoinColumn(name = "Trainee_id", unique = true)
    private Trainee trainee;

}
