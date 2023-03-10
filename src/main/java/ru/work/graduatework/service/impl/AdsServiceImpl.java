package ru.work.graduatework.service.impl;

import com.sun.jdi.ObjectCollectedException;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.work.graduatework.Entity.*;
import ru.work.graduatework.controller.AdsController;
import ru.work.graduatework.dto.AdsDto;
import ru.work.graduatework.dto.CreateAdsDto;
import ru.work.graduatework.dto.repository.AdsRepository;
import ru.work.graduatework.mapper.AdsMapper;
import ru.work.graduatework.service.AdsService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;

    private final Logger logger = LoggerFactory.getLogger(AdsController.class);
    public AdsServiceImpl(AdsRepository adsRepository) {
        this.adsRepository = adsRepository;
    }

    @Override
    public Collection<AdsDto> getAds() {
        logger.info("Current Method is - getAds-Service");
        return adsRepository.findAll().stream().map(AdsMapper::toDto).collect(Collectors.toList());
    }

    // TODO: здесь требуется доработать,пример был на разборе
    @Override
    public AdsDto addAds(CreateAdsDto createAdsDto, String image) {
        logger.info("Current Method is - addAds");
        // если креэйтДто.дет... null то ошибка
        Ads ads = new Ads();
        ads.setTitle(createAdsDto.getTitle());
        ads.setPrice(createAdsDto.getPrice());
        ads.setImage(image);
        ads.setAuthor(1);
        return AdsMapper.toDto(adsRepository.save(ads));
    }


    @Override
    public FullAds getFullAd() {
        return null;
    }

    @Override
    public AdsDto removeAds(int id) {
        Ads dbAds = this.adsRepository.findById(id).orElseThrow(ObjectCollectedException::new);
        this.adsRepository.delete(dbAds);
        return AdsMapper.toDto(dbAds);
    }

    @Override
    public Ads updateAds() {
        return null;
    }

    @Override
    public ResponseWrapperAds getAdsMe() {
        return null;
    }

    @Override
    public ResponseWrapperComment getComments() {
        return null;
    }

    @Override
    public Comment addComments() {
        return null;
    }

    @Override
    public Comment getCommentsId() {
        return null;
    }

    @Override
    public void deleteCommentsId() {

    }

    @Override
    public Comment updateCommentsId() {
        return null;
    }
}
