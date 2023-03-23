package ru.work.graduatework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.Entity.User;
import ru.work.graduatework.dto.CreateUserDto;
import ru.work.graduatework.dto.RegisterReqDto;
import ru.work.graduatework.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper extends MapperScheme<UserDto, User> {

  CreateUserDto toCreateUserDto(User entity);

  User createUserDtoToEntity(CreateUserDto dto);


  @Mapping(target = "id", ignore = true)
  @Mapping(target = "email", source = "username")
  @Mapping(target = "password", source = "password")
  @Mapping(target = "city", ignore = true)
  @Mapping(target = "regDate", ignore = true)
  @Mapping(target = "image", ignore = true)
  @Mapping(target = "role", defaultValue = "USER")
  User toEntity(RegisterReqDto dto);


  @Mapping(target = "password", ignore = true)
  @Mapping(target = "image", ignore = true)
  @Mapping(target = "role", ignore = true)
  User toEntity(UserDto dto);

  @Mapping(target = "image", source = "image", qualifiedByName = "imageMapping")
  UserDto toDto(User entity);

  @Named("imageMapping")
  default String imageMapping(Image image) {
    if (image == null) {
      return "";
    }
    return "/user/image/" + image.getId();

  }

}
