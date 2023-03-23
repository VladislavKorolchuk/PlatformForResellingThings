package ru.work.graduatework.dto;

import lombok.Data;

@Data
public class AdCommentDto {

    private int pk;

    private int author;

    private String createdAt;

    private String text;

    public AdCommentDto() {
    }

    public AdCommentDto(int pk, String createdAt, String text) {
        this.pk = pk;
        this.text = text;
        this.createdAt = createdAt;
    }

}
