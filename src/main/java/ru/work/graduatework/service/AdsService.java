package ru.work.graduatework.service;

import com.sun.jdi.ObjectCollectedException;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ru.work.graduatework.Entity.*;
import ru.work.graduatework.dto.*;
import ru.work.graduatework.mapper.AdsMapper;
import ru.work.graduatework.mapper.CommentMapper;
import ru.work.graduatework.repository.AdsRepository;
import ru.work.graduatework.repository.CommentRepository;
import ru.work.graduatework.repository.UsersRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.stream.Collectors;

import static ru.work.graduatework.security.SecurityUtils.getUserIdFromContext;

@Service
public class AdsService {

    private final Logger logger = LoggerFactory.getLogger(AdsService.class);

    private final UsersService userService;
    private final ImageService imageService;
    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;
    private final AdsMapper adsMapper;

    CommentMapper commentMapper;
    private final UsersRepository usersRepository;


    public AdsService(UsersService userService, ImageService imageService, AdsRepository adsRepository, CommentRepository adsCommentRepository, CommentRepository commentRepository, AdsMapper adsMapper, CommentMapper commentMapper, UsersRepository usersRepository) {
        this.userService = userService;
        this.imageService = imageService;
        this.adsRepository = adsRepository;
        this.commentRepository = commentRepository;
        this.adsMapper = adsMapper;
        this.commentMapper = commentMapper;
        this.usersRepository = usersRepository;
    }

    public Collection<Ads> getAllAds() {
        return adsRepository.findAll();
    }


    @SneakyThrows
    public Ads addAds(CreateAdsDto createAdsDto, MultipartFile adsImage) {
        logger.info("Current Method is - service AddAds");
        Ads ads = adsMapper.toEntity(createAdsDto);
//        Users user = userService.getUserById(getUserIdFromContext());
        Users user = usersRepository.findById(1L).orElseThrow();
        ads.setAuthor(user);
        ads.setImage(imageService.uploadImage(adsImage));
        return adsRepository.save(ads);
    }

    public Collection<Ads> getAdsMe() {
//        Users user = usersRepository.findByEmail(SecurityContextHolder.getContext()
//                .getAuthentication().getName()).orElseThrow(EntityNotFoundException::new);
        logger.info("Current Method is - service getAdsMe");
        Users user = usersRepository.findById(1L).orElseThrow();
        Collection<Ads> adsList = adsRepository.findAllByAuthorId(user.getId());
        return adsRepository.findAll().stream()
                .filter(ads -> ads.getAuthor().equals(user)).collect(Collectors.toList());
    }


    public Ads getAdsById(long adId) {
        logger.info("Current Method is - service getAdsById");
        return adsRepository.findById(adId).orElseThrow(EntityNotFoundException::new);
    }


    public void removeAds(long id) {
        logger.info("Current Method is - service removeAds");
        Ads ads = getAdsById(id);
        commentRepository.deleteAdsCommentsByAdId(id);
        this.adsRepository.delete(ads);
    }

    public Comment getAdsComment(long adPk, long id) {
        logger.info("Current Method is - service getAdsComment");
        return commentRepository.findByIdAndAdId(id, adPk).orElseThrow(ObjectCollectedException::new);
    }


    public Collection<Comment> getComments(long adPk) {
        logger.info("Current Method is - service getComments");
        return commentRepository.findAllByAdId(adPk);
    }

    public Comment addAdsComments(long adPk, AdsCommentDto commentDto) {
        logger.info("Current Method is - service addAdsComments");
        Comment adsComment = commentMapper.toEntity(commentDto);
//        Users user = usersRepository.findByEmail(SecurityContextHolder.getContext()
//                .getAuthentication().getName()).orElseThrow(EntityExistsException::new);
        Users user = usersRepository.findById(1L).orElseThrow();
        adsComment.setAuthor(user);
        adsComment.setAd(getAdsById(adPk));
        adsComment.setCreatedAt(userService.dateUserRegistration());
        return commentRepository.save(adsComment);
    }


    public void deleteAdsComment(long adPk, long id) {
        logger.info("Current Method is - service deleteAdsComment");
        Comment comment = commentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        commentRepository.delete(comment);
    }

    public Comment updateComments(long adPk, int id, Comment commentUpdated) {
        logger.info("Current Method is - service updateComments");
        Comment adsComment = getAdsComment(adPk, id);
        adsComment.setText(commentUpdated.getText());
        return commentRepository.save(adsComment);
    }

    @SneakyThrows
    public void updateAdsImage(long id, MultipartFile image) {
        logger.info("Current Method is - service updateAdsImage");
        Ads ads = getAdsById(id);
        ads.setImage(imageService.uploadImage(image));
        adsRepository.save(ads);
    }

    public Ads updateAds(long adId, CreateAdsDto createAdsDto) {
        logger.info("Current Method is - service updateAds");
        Ads ads = getAdsById(adId);
        ads.setTitle(createAdsDto.getTitle());
        ads.setDescription(createAdsDto.getDescription());
        ads.setPrice(createAdsDto.getPrice());
        return adsRepository.save(ads);
    }
}