package ru.work.graduatework.service;

import org.springframework.stereotype.Service;

@Service
public interface AdsService {
    String getAds();   // Получить объявление

    String addAds();   // Добавить объявления

    String getComments();  // Получить комментарии

    String addComments();  // Добавить Комментарии

    String getFullAd();   // Получить полную рекламу

    String removeAds();   // Убрать рекламу

    String updateAds();   // Обновить рекламу

    String getCommentsId();   // Получить комментарии по id

    String deleteCommentsId();   // Удалить комментарии по id

    String updateCommentsId();   // Обновление комментария по id

    String getAdsMe();   // Получить рекламу
}

