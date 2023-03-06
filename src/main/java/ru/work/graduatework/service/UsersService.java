package ru.work.graduatework.service;

import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.Entity.NewPassword;
import ru.work.graduatework.Entity.Users;

public interface UsersService {

    Users getUsers(); // Получить пользователя

    NewPassword setPassword();  // Установка пароля

    Users updateUser();   // Обновить пользователя

    Image updateUserImage();   // Обновление изображение пользователя

}


