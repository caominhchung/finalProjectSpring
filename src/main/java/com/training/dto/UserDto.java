package com.training.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String national;
    private String account;
    private String password;
    private String name;
    private String telNumber;
    private String facebook;
    private String email;
    private String gender;
    private String role;

    private boolean active;

}
