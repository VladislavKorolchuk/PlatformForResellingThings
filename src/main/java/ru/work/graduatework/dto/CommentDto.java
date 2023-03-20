package ru.work.graduatework.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CommentDto {

    private int pk;

    private int author;

    private String createdAt;

    @NotBlank
    @Size(min = 8)
    private String text;

}
