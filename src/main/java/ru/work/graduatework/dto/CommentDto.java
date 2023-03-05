package ru.work.graduatework.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Integer author;
    private String createdAt;
    private Integer pk;
    private String text;

    public CommentDto(Integer author, String createdAt, Integer pk, String text) {
        this.author = author;
        this.createdAt = createdAt;
        this.pk = pk;
        this.text = text;
    }
}
