package ru.work.graduatework.dto;

import lombok.Data;

@Data
public class UserDto {

    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String city;

    private String regDate;

    private String image;

    // private Collection<Ads> adsCollection;

    public UserDto() {

    }

    public UserDto(int id, String firstName, String lastName, String email, String phone,
                   String city, String regDate, String image) {
        //, Collection<Ads> adsCollection
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.regDate = regDate;
        this.image = image;
        //   this.adsCollection = adsCollection;
    }

}
