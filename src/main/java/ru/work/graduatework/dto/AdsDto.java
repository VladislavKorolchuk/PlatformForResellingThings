package ru.work.graduatework.dto;

import lombok.Data;
import ru.work.graduatework.Entity.Image;

import java.util.List;

@Data
public class AdsDto {
    private Integer author;
    private String description;
    private Integer pk;
    private Integer price;
    private String title;

    private Image images;

    public AdsDto(Integer author, String description, Image images, Integer pk, Integer price, String title) {
        this.description = description;
        this.author = author;
        this.images = images;
        this.pk = pk;
        this.price = price;
        this.title = title;
    }

}
