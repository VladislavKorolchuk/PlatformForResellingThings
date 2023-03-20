package ru.work.graduatework.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
public class AdsDto {

    private int author;

    private String image;

    private int pk;

    private int price;

    @NotBlank
    private String title;

    @NotBlank
    @JsonIgnore
    private String description;

}
