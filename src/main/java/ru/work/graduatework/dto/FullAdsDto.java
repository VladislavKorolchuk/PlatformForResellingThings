package ru.work.graduatework.dto;

import lombok.Data;

import java.util.List;

@Data
public class FullAdsDto {
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private List<String> image;
    private String phone;
    private Integer pk;
    private Integer price;
    private String title;
}
