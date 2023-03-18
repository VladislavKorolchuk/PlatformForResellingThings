package ru.work.graduatework.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AdsDto {

    private int author;
    private String images;
    private int pk;
    private int price;
    private String title;
    @JsonIgnore
    private String description;

    public AdsDto(int author, int pk, int price, String title, String images, String description) {
        this.author = author;
        this.pk = pk;
        this.price = price;
        this.title = title;
        this.images = images;
        this.description = description;
    }

}
