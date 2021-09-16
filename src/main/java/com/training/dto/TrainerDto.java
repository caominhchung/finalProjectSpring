package com.training.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor

public class TrainerDto {
    private Integer id;
    private String national;
    @NotBlank(message = "Account must not be blank!")
    private String account;

    @NotBlank(message = "Password must not be blank!")
    private String password;

    @NotBlank(message = "Name must not be blank!")
    private String name;

    @NotBlank(message = "Phone must not be blank!")
    @Pattern(regexp = "^\\d{10,15}", message = "Phone must have 10-15 numbers")
    private String telNumber;

    @NotBlank(message = "Facebook must not be blank!")
    private String facebook;

    @NotBlank(message = "Email must not be blank!")
    @Pattern(regexp = "[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}", message = "Email must have right format")
    private String email;

    @NotBlank
    private String gender;
    private String role;
}
