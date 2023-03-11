package ru.work.graduatework.service;

import ru.work.graduatework.Old_Entity.Image;
import ru.work.graduatework.Old_Entity.NewPassword;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.dto.UserDto;

import java.util.Collection;

public interface UsersService {

    Collection<Users> getUsers(); // Получить пользователя

    Users getUser(String emailUser);

    NewPassword setPassword();  // Установка пароля

    Users updateUser(UserDto userDto);   // Обновить пользователя

    Image updateUserImage();   // Обновление изображение пользователя

}


