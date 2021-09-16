//package com.training.entities;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "Event", uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"Trainee_id"})
//})
//@Getter
//@Setter
//@NoArgsConstructor
//public class Event {
//    @Id
//    @Column(name = "event_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    private String title;
//    private Date start;
//    private Date end;
//
//    @ManyToOne
//    @JoinColumn(name = "Trainee_id")
//    private Trainee trainee;
//}
