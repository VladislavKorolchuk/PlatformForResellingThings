package ru.work.graduatework.service;

import org.springframework.stereotype.Service;
import ru.work.graduatework.Entity.*;

@Service
public interface AdsService {

    ResponseWrapperAds getAds();   // Получить объявление

    CreateAds addAds();   // Добавить объявления

    FullAds getFullAd();   // Получить полную рекламу

    void removeAds();   // Убрать рекламу

    Ads updateAds();   // Обновить рекламу

    ResponseWrapperAds getAdsMe();   // Получить рекламу

    ResponseWrapperComment getComments(); //ResponseWrapperComment список всех комментариев

    Comment addComments(); //Комментарий которые добавили

    Comment getCommentsId(); // Комментарий по id

    void deleteCommentsId(); // void

    Comment updateCommentsId(); //comment

}

