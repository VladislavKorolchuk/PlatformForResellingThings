package ru.work.graduatework.service.impl;

import com.sun.jdi.ObjectCollectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.*;
import ru.work.graduatework.dto.*;
import ru.work.graduatework.dto.repository.AdsRepository;
import ru.work.graduatework.dto.repository.CommentRepository;
import ru.work.graduatework.dto.repository.UsersRepository;
import ru.work.graduatework.mapper.AdsMapper;
import ru.work.graduatework.mapper.CommentMapper;
import ru.work.graduatework.service.AdsService;

import java.io.IOException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdsServiceImpl implements AdsService {

    private final Logger logger = LoggerFactory.getLogger(AdsServiceImpl.class);

    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;
    private final UsersRepository usersRepository;
    private final ImageServiceImpl imageService;

    public AdsServiceImpl(AdsRepository adsRepository, CommentRepository commentRepository,
                          UsersRepository usersRepository, ImageServiceImpl imageService) {
        this.adsRepository = adsRepository;
        this.commentRepository = commentRepository;
        this.usersRepository = usersRepository;
        this.imageService = imageService;
    }

    @Override
    public ResponseWrapperAdsDto getAllAds() {
        logger.info("Current Method is - getAllAds-Service");
        ResponseWrapperAdsDto responseWrapperAdsDto = new ResponseWrapperAdsDto();
        List<Ads> dtoList = adsRepository.findAll();
        responseWrapperAdsDto.setCount(dtoList.size());
        responseWrapperAdsDto.setResults(dtoList);
        return responseWrapperAdsDto;
//        return adsRepository.findAll().stream().map(AdsMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ResponseWrapperAdsDto getAds(String title) {
        logger.info("Current Method is - getAds-Service");
        ResponseWrapperAdsDto responseWrapperAdsDto = new ResponseWrapperAdsDto();
        List<Ads> list = adsRepository.findByTitleIgnoreCase(title);
        responseWrapperAdsDto.setCount(list.size());
        responseWrapperAdsDto.setResults(list);
        return responseWrapperAdsDto;
    }

    // TODO: добавлять пользователя
    @Override
    public AdsDto addAds(CreateAdsDto createAdsDto, MultipartFile adsImage) {
        logger.info("Current Method is - serviceAddAds");
//        Users users = usersRepository.findByEmail((SecurityContextHolder
//                .getContext().getAuthentication().getName())).orElseThrow();  //не работает
        Ads ads = new Ads();
        ads.setTitle(createAdsDto.getTitle());
        ads.setPrice(createAdsDto.getPrice());
        ads.setDescription(createAdsDto.getDescription());
        ads.setAuthor(1);
//        ads.setAuthor(users.getId()); не работает
        adsRepository.save(ads);
        try {
            Image image = imageService.addAdsImage(ads.getPk(), adsImage);
            ads.setImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return AdsMapper.toDto(ads);
    }


    @Override
    public FullAdsDto getFullAd(int id) {
        Users users = usersRepository.findByEmail((SecurityContextHolder
                .getContext().getAuthentication().getName())).orElseThrow();
        Ads ads = adsRepository.findById(id).orElseThrow();
        FullAdsDto fullAdsDto = new FullAdsDto();
        fullAdsDto.setAuthorFirstName(users.getFirstName());
        fullAdsDto.setAuthorLastName(users.getLastName());
        fullAdsDto.setDescription(ads.getDescription());
        fullAdsDto.setEmail(users.getEmail());
        fullAdsDto.setPrice(ads.getPrice());
        fullAdsDto.setTitle(ads.getTitle());
        return fullAdsDto;
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
        Users usersSecurity = usersRepository.findByEmail((SecurityContextHolder.
        getContext().getAuthentication().getName())).orElseThrow();
        Users users = usersRepository.findById(adsDto.getAuthor()).orElseThrow();
        if (users.equals(usersSecurity)){
           ads.setAuthor(adsDto.getAuthor());
           ads.setTitle(adsDto.getTitle());
           ads.setDescription(adsDto.getDescription());
           ads.setPrice(adsDto.getPrice());
           adsRepository.save(ads);
           return AdsMapper.toDto(ads);
        }
        return adsDto;
    }

    @Override
    public ResponseWrapperAdsDto getAdsMe() {
        return null;
    }

    @Override
    public ResponseWrapperCommentDto getComments(Integer ad_pk) {
        ResponseWrapperCommentDto responseWrapperCommentDto = new ResponseWrapperCommentDto();
        Ads ads = adsRepository.findById(ad_pk).orElseThrow();
        List<Comment> dtoList = new ArrayList<>(ads.getCommentCollection());
        responseWrapperCommentDto.setCount(dtoList.size());
        responseWrapperCommentDto.setResults(dtoList);
        return responseWrapperCommentDto;
    }

    @Override
    @Transactional
    public CommentDto addComments(int ad_pk, CommentDto commentDto) {
        logger.info("Current Method is - addCommentsService");
        Ads ads = this.adsRepository.findById(ad_pk).orElseThrow();
        Comment comment = CommentMapper.toEntity(commentDto);
        ads.getCommentCollection().add(comment);
        return CommentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto getCommentsId() {
        return null;
    }

    @Override
    public void deleteCommentsId() {
    }

    @Override
    public CommentDto updateCommentsId() {
        return null;
    }
}
