package ru.work.graduatework.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.work.graduatework.Entity.Comment;
import ru.work.graduatework.dto.AdsCommentDto;

@Mapper(componentModel = "spring")
public interface CommentMapper extends MapperScheme<AdsCommentDto, Comment> {

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Comment toEntity(AdsCommentDto dto);

    @Mapping(target = "author", source = "author.id")
    @Mapping(source = "id", target = "pk")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    AdsCommentDto toDto(Comment entity);

}
