package ru.work.graduatework.service;

import com.sun.jdi.ObjectCollectedException;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ru.work.graduatework.Entity.*;
import ru.work.graduatework.dto.*;
import ru.work.graduatework.mapper.AdMapper;
import ru.work.graduatework.mapper.CommentMapper;
import ru.work.graduatework.repository.AdRepository;
import ru.work.graduatework.repository.CommentRepository;
import ru.work.graduatework.repository.UsersRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AdsService {

    private final Logger logger = LoggerFactory.getLogger(AdsService.class);

    private final UserService userService;
    private final ImageService imageService;
    private final AdRepository adRepository;
    private final CommentRepository commentRepository;
    private final AdMapper adMapper;

    CommentMapper commentMapper;
    private final UsersRepository usersRepository;


    public AdsService(UserService userService, ImageService imageService, AdRepository adRepository, CommentRepository adsCommentRepository, CommentRepository commentRepository, AdMapper adMapper, CommentMapper commentMapper, UsersRepository usersRepository) {
        this.userService = userService;
        this.imageService = imageService;
        this.adRepository = adRepository;
        this.commentRepository = commentRepository;
        this.adMapper = adMapper;
        this.commentMapper = commentMapper;
        this.usersRepository = usersRepository;
    }

    public Collection<Ad> getAllAds() {
        return adRepository.findAll();
    }


    @SneakyThrows
    public Ad addAds(CreateAdDto createAdDto, MultipartFile adsImage) {
        logger.info("Current Method is - service AddAds");
        Ad ad = adMapper.toEntity(createAdDto);
//        Users user = userService.getUserById(getUserIdFromContext());
        Users user = usersRepository.findById(1L).orElseThrow();
        ad.setAuthor(user);
        ad.setImage(imageService.uploadImage(adsImage));
        return adRepository.save(ad);
    }

    public Collection<Ad> getAdsMe() {
//        Users user = usersRepository.findByEmail(SecurityContextHolder.getContext()
//                .getAuthentication().getName()).orElseThrow(EntityNotFoundException::new);
        logger.info("Current Method is - service getAdsMe");
        Users user = usersRepository.findById(1L).orElseThrow();
        Collection<Ad> adList = adRepository.findAllByAuthorId(user.getId());
        return adRepository.findAll().stream()
                .filter(ads -> ads.getAuthor().equals(user)).collect(Collectors.toList());
    }


    public Ad getAdsById(long adId) {
        logger.info("Current Method is - service getAdsById");
        return adRepository.findById(adId).orElseThrow(EntityNotFoundException::new);
    }


    public void removeAds(long id) {
        logger.info("Current Method is - service removeAds");
        Ad ad = getAdsById(id);
        commentRepository.deleteAdsCommentsByAdId(id);
        this.adRepository.delete(ad);
    }

    public Comment getAdsComment(long adPk, long id) {
        logger.info("Current Method is - service getAdsComment");
        return commentRepository.findByIdAndAdId(id, adPk).orElseThrow(ObjectCollectedException::new);
    }


    public Collection<Comment> getComments(long adPk) {
        logger.info("Current Method is - service getComments");
        return commentRepository.findAllByAdId(adPk);
    }

    public Comment addAdsComments(long adPk, AdCommentDto commentDto) {
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
        Ad ad = getAdsById(id);
        ad.setImage(imageService.uploadImage(image));
        adRepository.save(ad);
    }

    public Ad updateAds(long adId, CreateAdDto createAdDto) {
        logger.info("Current Method is - service updateAds");
        Ad ad = getAdsById(adId);
        ad.setTitle(createAdDto.getTitle());
        ad.setDescription(createAdDto.getDescription());
        ad.setPrice(createAdDto.getPrice());
        return adRepository.save(ad);
    }
}