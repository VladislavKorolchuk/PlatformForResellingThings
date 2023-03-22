package ru.work.graduatework.dto;

import lombok.Data;

@Data
public class AdCommentDto {

    private int pk;

    private int author;

    private String createdAt;

    private String text;


}
