package ru.work.graduatework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.work.graduatework.Entity.Comment;
import ru.work.graduatework.dto.CommentDto;

@Mapper(componentModel = "spring")
public interface AdsCommentMapper extends MapperScheme<CommentDto, Comment>{
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "id", source = "pk")
    @Mapping(target = "createdAt", ignore = true)
    Comment toEntity(CommentDto dto);

    @Mapping(target = "author", source = "author.id")
    @Mapping(source = "id", target = "pk")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    CommentDto toDto(Comment entity);
}
