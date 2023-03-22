package ru.work.graduatework.dto;

import lombok.Data;

@Data
public class RegisterReqDto {

    private String username;      // User's email address

    private String password;      // User password

    private String firstName;     // User's name

    private String lastName;      // User's last name

    private String phone;         // User's phone number

    private Role role;            // User's role

}
