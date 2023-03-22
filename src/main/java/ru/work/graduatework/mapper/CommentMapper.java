package ru.work.graduatework.mapper;

import org.mapstruct.Mapping;
import ru.work.graduatework.Entity.Comment;
import ru.work.graduatework.dto.AdCommentDto;

public interface CommentMapper extends MapperScheme<AdCommentDto, Comment> {

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "id", source="pk")
    @Mapping(target = "createAt", ignore = true)
    Comment toEntity(AdCommentDto dto);

    @Mapping(target = "author", source = "author.id")
    @Mapping(source = "id", target = "pk")
    @Mapping(target = "createAt", source = "entity.CreateAt")
    AdCommentDto toDto(Comment entity);

}
