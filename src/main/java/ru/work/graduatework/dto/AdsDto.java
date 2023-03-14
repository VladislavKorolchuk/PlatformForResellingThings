package ru.work.graduatework.dto;

import lombok.Data;
import ru.work.graduatework.Entity.Image;

import java.util.List;

@Data
public class AdsDto {
    private Integer author;
    private Integer pk;
    private Integer price;
    private String title;

    private List<Image> images;

    public AdsDto(Integer author, List<Image> images, Integer pk, Integer price, String title) {
        this.author = author;
        this.images = images;
        this.pk = pk;
        this.price = price;
        this.title = title;
    }

}
