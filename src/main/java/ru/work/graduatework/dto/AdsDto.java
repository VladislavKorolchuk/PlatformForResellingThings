package ru.work.graduatework.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.work.graduatework.Entity.Image;


@Data
public class AdsDto {

    private int author;
    private String images;
    private int pk;
    private int price;
    private String title;

    @JsonIgnore
    private String description;

    public AdsDto(int author, String images, int pk, int price, String title, String description) {
        this.author = author;
        this.images = images;
        this.pk = pk;
        this.price = price;
        this.title = title;
        this.description = description;
    }

    public AdsDto(Integer author, Image image, Integer pk, Integer price, String title, String description) {

    }
}
