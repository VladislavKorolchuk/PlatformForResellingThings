package ru.work.graduatework.dto;

import lombok.Data;

@Data
public class CreateAdsDto {
    private String description;
    private Integer price;
    private String title;

    public CreateAdsDto(String description, Integer price, String title) {
        this.description = description;
        this.price = price;
        this.title = title;
    }
}
