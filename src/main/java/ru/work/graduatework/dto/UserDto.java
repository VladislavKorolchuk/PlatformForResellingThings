package ru.work.graduatework.dto;

import lombok.Data;

@Data
public class UserDto {

    private String phone;
    private String lastName;
    private String firstName;
    private String email;
    private Integer id;
    private String regDate;
    private String city;
    private String image;
}
