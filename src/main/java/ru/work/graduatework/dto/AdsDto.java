package ru.work.graduatework.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdsDto {
    private Integer author;
    private List<String> image;
    private Integer pk;
    private Integer price;
    private String title;

    public AdsDto(Integer author, List<String> image, Integer pk, Integer price, String title) {
        this.author = author;
        this.image = image;
        this.pk = pk;
        this.price = price;
        this.title = title;
    }
}
