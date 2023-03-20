package ru.work.graduatework.dto;

import lombok.Data;
import ru.work.graduatework.Entity.Image;

import java.util.List;

@Data
public class FullAdsDto {

    private String image;
    private String authorLastName;
    private String authorFirstName;
    private String phone;
    private int price;
    private String description;
    private int pk;
    private String title;
    private String email;

}
