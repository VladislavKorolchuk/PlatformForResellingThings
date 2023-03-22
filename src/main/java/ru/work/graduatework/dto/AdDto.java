package ru.work.graduatework.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AdDto {

    private int author;

    private String image;
    private int pk;
    private int price;
    //  @NotBlank
    private String title;
    //  @NotBlank
    @JsonIgnore
    private String description;

    public AdDto(int author, String image, int pk, int price, String title, String description) {
        this.author = author;
        this.image = image;
        this.pk = pk;
        this.price = price;
        this.title = title;
        this.description = description;
    }
}
