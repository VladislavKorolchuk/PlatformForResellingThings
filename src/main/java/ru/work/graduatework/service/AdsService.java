package ru.work.graduatework.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.*;
import ru.work.graduatework.dto.AdsDto;
import ru.work.graduatework.dto.CommentDto;
import ru.work.graduatework.dto.CreateAdsDto;
import ru.work.graduatework.dto.ResponseWrapperAdsDto;

import java.util.Collection;
import java.util.List;

@Service
public interface AdsService {


    ResponseWrapperAdsDto getAds();   // Получить объявление

    AdsDto addAds(CreateAdsDto createAdsDto, MultipartFile adsImage);

    FullAds getFullAd();   // Получить полную рекламу

    AdsDto removeAds(int id);   // Убрать рекламу

    AdsDto updateAds(int id, AdsDto adsDto);   // Обновить рекламу

    ResponseWrapperAds getAdsMe();   // Получить рекламу

    ResponseWrapperComment getComments(); //ResponseWrapperComment список всех комментариев

    public CommentDto addComments(int ad_pk, CommentDto commentDto); //Комментарий которые добавили

    Comment getCommentsId(); // Комментарий по id

    void deleteCommentsId(); // void

    Comment updateCommentsId(); //comment

}

