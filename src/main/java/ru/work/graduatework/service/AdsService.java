package ru.work.graduatework.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.*;
import ru.work.graduatework.dto.*;

@Service
public interface AdsService {


    ResponseWrapperAdsDto getAllAds();   // Получить объявление

    public ResponseWrapperAdsDto getAds(String title);

    AdsDto addAds(CreateAdsDto createAdsDto, MultipartFile adsImage);

    FullAdsDto getFullAd(int id);   // Получить полную рекламу

    AdsDto removeAds(int id);   // Убрать рекламу

    AdsDto updateAds(int id, AdsDto adsDto);   // Обновить рекламу

    ResponseWrapperAdsDto getAdsMe();   // Получить рекламу

    ResponseWrapperCommentDto getComments(Integer ad_pk); //ResponseWrapperComment список всех комментариев

    public CommentDto addComments(int ad_pk, CommentDto commentDto); //Комментарий которые добавили

    CommentDto getCommentsId(); // Комментарий по id

    void deleteCommentsId(); // void

    CommentDto updateCommentsId(); //comment

}

