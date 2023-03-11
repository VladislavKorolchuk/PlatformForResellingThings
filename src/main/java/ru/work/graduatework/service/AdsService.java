package ru.work.graduatework.service;

import org.springframework.stereotype.Service;
import ru.work.graduatework.Entity.*;
import ru.work.graduatework.Entity.FullAds;
import ru.work.graduatework.Entity.ResponseWrapperAds;
import ru.work.graduatework.Entity.ResponseWrapperComment;
import ru.work.graduatework.dto.AdsDto;
import ru.work.graduatework.dto.CreateAdsDto;

import java.util.Collection;

@Service
public interface AdsService {


    Collection<AdsDto> getAds();   // Получить объявление

    AdsDto addAds(CreateAdsDto createAdsDto, String image);

    FullAds getFullAd();   // Получить полную рекламу

    AdsDto removeAds(int id);   // Убрать рекламу

    Ads updateAds();   // Обновить рекламу

    ResponseWrapperAds getAdsMe();   // Получить рекламу

    ResponseWrapperComment getComments(); //ResponseWrapperComment список всех комментариев

    Comment addComments(); //Комментарий которые добавили

    Comment getCommentsId(); // Комментарий по id

    void deleteCommentsId(); // void

    Comment updateCommentsId(); //comment

}

