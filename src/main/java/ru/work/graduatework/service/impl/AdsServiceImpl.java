package ru.work.graduatework.service.impl;

import com.sun.jdi.ObjectCollectedException;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.work.graduatework.Entity.*;
import ru.work.graduatework.controller.AdsController;
import ru.work.graduatework.dto.AdsDto;
import ru.work.graduatework.dto.CommentDto;
import ru.work.graduatework.dto.CreateAdsDto;
import ru.work.graduatework.dto.repository.AdsRepository;
import ru.work.graduatework.dto.repository.CommentRepository;
import ru.work.graduatework.dto.repository.UsersRepository;
import ru.work.graduatework.mapper.AdsMapper;
import ru.work.graduatework.mapper.CommentMapper;
import ru.work.graduatework.service.AdsService;
import ru.work.graduatework.service.UsersService;

import java.security.Principal;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;

    private final UsersService usersService;


    private final Logger logger = LoggerFactory.getLogger(AdsController.class);
    private final UsersRepository usersRepository;

    public AdsServiceImpl(AdsRepository adsRepository, CommentRepository commentRepository,
                          UsersRepository usersRepository, UsersService usersService) {
        this.adsRepository = adsRepository;
        this.commentRepository = commentRepository;
        this.usersRepository = usersRepository;
        this.usersService = usersService;
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
        Users users = usersRepository.findByEmail((SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
        Ads ads = new Ads();
        ads.setTitle(createAdsDto.getTitle());
        ads.setPrice(createAdsDto.getPrice());
        ads.setImage(image);
        ads.setAuthor(users.getId());
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
    public AdsDto updateAds(int id, AdsDto adsDto) {
        Ads ads = adsRepository.findById(id).orElseThrow((ObjectCollectedException::new));
//        Users usersSecurity = usersRepository.findByEmail((SecurityContextHolder.getContext().getAuthentication().getName())).orElseThrow();
//        Users users = usersRepository.findById(adsDto.getAuthor()).orElseThrow();
//        if (users.equals(usersSecurity)){
//            return AdsMapper.toDto(this.adsRepository.save(AdsMapper.toEntity(adsDto)));
//        }
        return adsDto;
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
    public CommentDto addComments(int ad_pk, CommentDto commentDto) {
//обработать возможные ошибки с CommentDto
        Ads ads = this.adsRepository.findById(ad_pk).orElseThrow(ObjectCollectedException::new);
        ads.getCommentCollection().add(CommentMapper.toEntity(commentDto));
        return CommentMapper.toDto(this.commentRepository.save(CommentMapper.toEntity(commentDto)));
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
