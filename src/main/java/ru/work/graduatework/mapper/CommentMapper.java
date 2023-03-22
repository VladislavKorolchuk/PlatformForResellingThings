package ru.work.graduatework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.work.graduatework.Entity.Comment;
import ru.work.graduatework.dto.AdCommentDto;

@Mapper(componentModel = "spring")
public interface CommentMapper extends MapperScheme<AdCommentDto, Comment> {
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "id", source = "pk")
    @Mapping(target = "createdAt", ignore = true)
    Comment toEntity(AdCommentDto dto);

    @Mapping(target = "author", source = "author.id")
    @Mapping(source = "id", target = "pk")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    AdCommentDto toDto(Comment entity);
}
