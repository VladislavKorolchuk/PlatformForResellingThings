package ru.work.graduatework.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


@Data
public class AdsDto {
    private int author;
    private int pk;
    private int price;
    private String title;

    private byte[] images;

    @JsonIgnore
    private String description;

    public AdsDto(int author, int pk, int price, String title, byte[] images, String description) {
        this.author = author;
        this.pk = pk;
        this.price = price;
        this.title = title;
        this.images = images;
        this.description = description;
    }
}
