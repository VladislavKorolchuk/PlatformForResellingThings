package ru.work.graduatework.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.work.graduatework.service.AdsService;

@RestController
@RequiredArgsConstructor
public class AdsController {
    private final AdsService adsService;

    @GetMapping("/ads")    // Получить объявление
    public String getAds() {
        return adsService.getAds();
    }

    @PostMapping("/ads")   // Добавить объявления
    public String addAds() {
        return adsService.addAds();
    }

    @GetMapping("/ads/{ad_pk}/comments")  // Получить комментарии
    public String getComments() {
        return adsService.getComments();
    }

    @PostMapping("/ads/{ad_pk}/comments")  // Добавить Комментарии
    public String addComments() {
        return adsService.addComments();
    }

    @GetMapping("/ads/{id}")   // Получить полную рекламу
    public String getFullAd() {
        return adsService.getFullAd();
    }

    @DeleteMapping("/ads/{id}")   // Убрать рекламу
    public String removeAds() {
        return adsService.removeAds();
    }

    @PatchMapping("/ads/{id}")   // Обновить рекламу
    public String updateAds() {
        return adsService.updateAds();
    }

    @GetMapping("/ads/{ad_pk}/comments/{id}")   // Получить комментарии по id
    public String getCommentsId() {
        return adsService.getCommentsId();
    }

    @DeleteMapping("/ads/{ad_pk}/comments/{id}")   // Удалить комментарии по id
    public String deleteCommentsId() {
        return adsService.deleteCommentsId();
    }

    @PatchMapping("/ads/{ad_pk}/comments/{id}")    // Обновление комментария по id
    public String updateCommentsId() {
        return adsService.updateCommentsId();
    }

    @GetMapping("/ads/me")   // Получить рекламу
    public String getAdsMe() {
        return adsService.getAdsMe();
    }
}
