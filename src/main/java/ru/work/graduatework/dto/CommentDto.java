package ru.work.graduatework.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Integer author;
    private String createdAt;
    private Integer pk;
    private String text;
}
