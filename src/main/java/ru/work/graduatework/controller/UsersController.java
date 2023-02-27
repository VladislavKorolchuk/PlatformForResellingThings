package ru.work.graduatework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.work.graduatework.service.UsersService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @GetMapping("/users/me")   // Получить пользователя
    public String getUsers() {
        return usersService.getUsers();
    }

    @PostMapping("/users/set_password")  // Установка пароля
    public String setPassword() {
        return usersService.setPassword();
    }

    @PatchMapping("/users/me")  // Обновить пользователя
    public String updateUser() {
        return usersService.updateUser();
    }

    @PatchMapping("/users/me/image") // Обновление изображение пользователя
    public String updateUserImage() {
        return usersService.updateUserImage();
    }


}
