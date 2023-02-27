package ru.work.graduatework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.work.graduatework.service.UsersService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;
    public ResponseEntity<?> getAds() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/me")   // Получить пользователя
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/set_password")  // Установка пароля
    public ResponseEntity<?> setPassword() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/me")  // Обновить пользователя
    public ResponseEntity<?> updateUser() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/me/image") // Обновление изображение пользователя
    public ResponseEntity<?> updateUserImage() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
