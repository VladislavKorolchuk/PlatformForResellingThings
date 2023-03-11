package ru.work.graduatework.service;

import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.Entity.NewPassword;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.dto.UserDto;

import java.util.Collection;
import java.util.Optional;

public interface UsersService {

    Collection<Users> getUsers(); // Получить пользователей

    Users getUser(String emailUser); // Получить пользователя

    NewPassword setPassword();  // Установка пароля

    Users updateUser(UserDto userDto);   // Обновить пользователя

    Image updateUserImage();   // Обновление изображение пользователя

}


