package com.training.dto;

import com.training.entities.enumeration.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class CreateTraineeDto {

    private String national;

    @NotBlank
    private String account;

    @NotBlank
    private String password;

    @NotBlank
    private String name;
    @NotBlank
    @Pattern(regexp = "^\\d{10,15}", message = "Phone must have 10-15 number")
    private String telNumber;
    private String facebook;
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}", message = "Email must have right format")
    private String email;
    @NotBlank
    private String gender;
    private String university;
    private String note;
    private Integer classOfTrainee;

    public CreateTraineeDto(String national, String account, String password, String name, String telNumber, String facebook, String email, String gender, String university, String note) {
        this.national = national;
        this.account = account;
        this.password = password;
        this.name = name;
        this.telNumber = telNumber;
        this.facebook = facebook;
        this.email = email;
        this.gender = gender;
        this.university = university;
        this.note = note;
    }

}
