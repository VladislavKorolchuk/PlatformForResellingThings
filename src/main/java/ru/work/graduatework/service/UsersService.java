package ru.work.graduatework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.dto.ImageDto;
import ru.work.graduatework.dto.NewPasswordDto;
import ru.work.graduatework.dto.UserDto;

import java.util.Collection;

public interface UsersService {

    Collection<Users> getUsers(); // Получить пользователя

    Users getUser(String emailUser);

    NewPasswordDto setPassword();  // Установка пароля

    UserDto updateUser(UserDto userDto);   // Обновить пользователя

    ImageDto updateUserImage(Integer id, MultipartFile imageDto);   // Обновление изображение пользователя

}


