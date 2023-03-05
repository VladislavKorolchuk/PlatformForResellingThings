package ru.work.graduatework.mapper;

import ru.work.graduatework.Entity.ResponseWrapperComment;
import ru.work.graduatework.dto.ResponseWrapperCommentDto;

public class ResponseWrapperCommentMapper {
    public static ResponseWrapperCommentDto toDto(ResponseWrapperComment responseWrapperComment) {
        return new ResponseWrapperCommentDto(responseWrapperComment.getCount(), responseWrapperComment.getResults());
    }

    public static ResponseWrapperComment toEntity(ResponseWrapperCommentDto responseWrapperCommentDto) {
        ResponseWrapperComment responseWrapperComment = new ResponseWrapperComment();
        responseWrapperComment.setCount(responseWrapperCommentDto.getCount());
        responseWrapperComment.setResults(responseWrapperCommentDto.getResults());
        return responseWrapperComment;
    }
}
