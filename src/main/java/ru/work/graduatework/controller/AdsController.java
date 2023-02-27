package ru.work.graduatework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.work.graduatework.dto.LoginReq;
import ru.work.graduatework.service.AdsService;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdsController {
    private final AdsService adsService;

    @GetMapping()    // Получить объявление
    public ResponseEntity<?>  getAds() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping()   // Добавить объявления
    public ResponseEntity<?> addAds() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{ad_pk}/comments")  // Получить комментарии
    public ResponseEntity<?> getComments() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{ad_pk}/comments")  // Добавить Комментарии
    public ResponseEntity<?> addComments() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")   // Получить полную рекламу
    public ResponseEntity<?> getFullAd() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/ads/{id}")   // Убрать рекламу
    public ResponseEntity<?> removeAds() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/ads/{id}")   // Обновить рекламу
    public ResponseEntity<?> updateAds() {
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @GetMapping("/ads/{ad_pk}/comments/{id}")   // Получить комментарии по id
    public ResponseEntity<?> getCommentsId() {
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @DeleteMapping("/ads/{ad_pk}/comments/{id}")   // Удалить комментарии по id
    public ResponseEntity<?> deleteCommentsId() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/ads/{ad_pk}/comments/{id}")    // Обновление комментария по id
    public ResponseEntity<?> updateCommentsId() {
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @GetMapping("/ads/me")   // Получить рекламу
    public ResponseEntity<?> getAdsMe() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
