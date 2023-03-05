package ru.work.graduatework.dto;

import lombok.Data;
import ru.work.graduatework.Entity.Ads;

import java.util.Collection;

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
    private Collection<Ads> ads;

    public UserDto(Integer id, String firstName, String lastName, String phone,
                   String email, String regDate, String city, String image, Collection<Ads> ads) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.regDate = regDate;
        this.city = city;
        this.image = image;
        this.ads = ads;
    }
}
