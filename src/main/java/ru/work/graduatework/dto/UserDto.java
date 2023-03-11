package ru.work.graduatework.dto;

import lombok.Data;

@Data
public class UserDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    private String regDate;

    private String city;

    private String image;

    public UserDto() {

    }

    public UserDto(Integer id, String firstName, String lastName, String phone,
                   String email, String regDate, String city, String image) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.regDate = regDate;
        this.city = city;
        this.image = image;
    }

}
