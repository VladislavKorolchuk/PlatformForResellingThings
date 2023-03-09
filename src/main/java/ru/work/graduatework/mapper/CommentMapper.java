package ru.work.graduatework.mapper;

import ru.work.graduatework.Entity.Comment;
import ru.work.graduatework.dto.CommentDto;

public class CommentMapper {

    public static CommentDto toDto(Comment comment){
        return new CommentDto(comment.getAuthor(), comment.getCreatedAt(), comment.getPk(), comment.getText());
    }

    public static Comment toEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setAuthor(commentDto.getAuthor());
        comment.setCreatedAt(commentDto.getCreatedAt());
        comment.setPk(commentDto.getPk());
        comment.setText(commentDto.getText());
        return comment;
    }

}
