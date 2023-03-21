package ru.work.graduatework.dto;

import lombok.Data;
import ru.work.graduatework.Entity.Comment;
import java.util.List;

@Data
public class ResponseWrapperCommentDto {

    private Integer count;
    private List<Comment> results;

}
