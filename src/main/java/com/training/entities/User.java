package com.training.entities;

import com.training.entities.enumeration.Gender;
import com.training.entities.enumeration.Role;
import com.training.entities.enumeration.converter.GenderConverter;
import com.training.entities.enumeration.converter.RoleConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "User")
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
@Getter
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_id")
    private Integer id;

    @Column(name = "National")
    private String national;

    @Column(name = "Account", unique = true)
    private String account;

    @Column(name = "Password")
    private String password;

    @Column(name = "Name")
    private String name;

    @Column(name = "TelPhone", unique = true)
    private String telNumber;

    @Column(name = "Facebook", unique = true)
    private String facebook;

    @Column(name = "Email", unique = true)
    private String email;

    @Column(name = "Gender")
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @Column(name = "Role")
    @Convert(converter = RoleConverter.class)
    private Role role;

    @Column(name = "active")
    private boolean active;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Attendance> attendanceList = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", national='" + national + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", telNumber='" + telNumber + '\'' +
                ", facebook='" + facebook + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", role=" + role +
                ", attendanceList=" + attendanceList +
                '}';
    }
}
