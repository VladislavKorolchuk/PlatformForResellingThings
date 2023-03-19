package ru.work.graduatework.dto;

import lombok.Data;

@Data
public class AdsCommentDto {

    private Integer pk;

    private int author;

    private String createdAt;

    private String text;

    public AdsCommentDto( Integer pk, String createdAt,  String text) {
        this.pk = pk;
        this.text = text;
        this.createdAt = createdAt;
    }

}
