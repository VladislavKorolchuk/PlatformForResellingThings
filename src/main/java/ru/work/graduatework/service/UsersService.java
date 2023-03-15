package ru.work.graduatework.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.Entity.NewPassword;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.dto.ImageDto;
import ru.work.graduatework.dto.UserDto;

import java.util.Collection;

public interface UsersService {

    Collection<Users> getUsers(); // Получить пользователя

    Users getUser(String emailUser);

    NewPassword setPassword();  // Установка пароля

    Users updateUser(UserDto userDto);   // Обновить пользователя

    ImageDto updateUserImage(Integer id, MultipartFile imageDto);   // Обновление изображение пользователя

}


