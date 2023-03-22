package ru.work.graduatework.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdsCommentDto {

    private int pk;

    private int author;

    private String createdAt;

    private String text;

    public AdsCommentDto( int pk, String createdAt,  String text) {
        this.pk = pk;
        this.text = text;
        this.createdAt = createdAt;
    }

}
