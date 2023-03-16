package ru.work.graduatework.dto;

import lombok.Data;
import ru.work.graduatework.Entity.Image;

import java.util.List;

@Data
public class FullAdsDto {

    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private Image image;
    private String phone;
    private Integer pk;
    private Integer price;
    private String title;

//    public FullAdsDto(String authorFirstName, String authorLastName, String description, String email,
//                      List<String> image, String phone, Integer pk, Integer price, String title) {
//        this.authorFirstName = authorFirstName;
//        this.authorLastName = authorLastName;
//        this.description = description;
//        this.email = email;
//        this.image = image;
//        this.phone = phone;
//        this.pk = pk;
//        this.price = price;
//        this.title = title;
//    }

}
