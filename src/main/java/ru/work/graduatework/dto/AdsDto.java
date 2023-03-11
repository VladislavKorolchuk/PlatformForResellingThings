package ru.work.graduatework.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdsDto {
    private Integer author;
    private String image;
    private Integer pk;
    private Integer price;
    private String title;

    public AdsDto(Integer author, String image, Integer pk, Integer price, String title) {
        this.author = author;
        this.image = image;
        this.pk = pk;
        this.price = price;
        this.title = title;
    }

}
