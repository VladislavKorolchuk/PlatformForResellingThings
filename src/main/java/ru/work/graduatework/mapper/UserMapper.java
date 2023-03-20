package ru.work.graduatework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.dto.CreateUserDto;
import ru.work.graduatework.dto.RegisterReqDto;
import ru.work.graduatework.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper extends MapperScheme<UserDto, Users> {
    CreateUserDto toCreateUserDto(Users entity);

    Users createUserDtoToEntity(CreateUserDto dto);

    @Mapping(target = "role", defaultValue = "USER")
    @Mapping(source = "username", target = "email")
    Users toEntity(RegisterReqDto dto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "role", ignore = true)
    Users toEntity(UserDto dto);

    @Mapping(target = "image", expression = "java(\"/ads/images/\" + entity.getImage().getId())")
    UserDto toDto(Users entity);

//    @Named("imageMapping")
//    default byte[] imageMapping(Image image) {
//        if (image == null) {
//            return null;
//        }
//        return image.getData();
//
//    }

}
