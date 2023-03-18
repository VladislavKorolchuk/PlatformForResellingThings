package ru.work.graduatework.dto;

import lombok.Data;

@Data
public class CommentDto {

    private String createdAt;
    private Integer pk;

    private int author;
    private String text;

    public CommentDto(String createdAt, Integer pk, String text) {
        this.createdAt = createdAt;
        this.pk = pk;
        this.text = text;
    }

}
