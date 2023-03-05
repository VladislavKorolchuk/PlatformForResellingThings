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

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
