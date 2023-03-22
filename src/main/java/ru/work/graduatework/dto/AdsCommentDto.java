package ru.work.graduatework.dto;

import lombok.Data;


@Data
public class AdsCommentDto {

    private long pk;

    private int author;

    private String createdAt;

    private String text;


}
