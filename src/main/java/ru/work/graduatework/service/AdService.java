package ru.work.graduatework.service;

import com.sun.jdi.ObjectCollectedException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;
import ru.work.graduatework.Entity.*;
import ru.work.graduatework.dto.*;
import ru.work.graduatework.mapper.AdMapper;
import ru.work.graduatework.mapper.CommentMapper;
import ru.work.graduatework.repository.AdRepository;
import ru.work.graduatework.repository.CommentRepository;
import ru.work.graduatework.repository.ImageRepository;
import ru.work.graduatework.repository.UserRepository;
import ru.work.graduatework.security.SecurityUtils;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import static ru.work.graduatework.security.SecurityUtils.checkPermissionToAds;
import static ru.work.graduatework.security.SecurityUtils.getUserIdFromContext;

@Service
@RequiredArgsConstructor
public class AdService {

    private final Logger logger = LoggerFactory.getLogger(AdService.class);


    private UserService userService;
    private final AdRepository adRepository;
    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final AdMapper adMapper;
    private final CommentMapper commentMapper;


    // Uses method - getAllAds    controller - AdsController
    public Collection<Ad> getAllAds() {
        return adRepository.findAll();
    }


    public ResponseWrapperAdDto getAds(String title) {
        logger.info("Current Method is - getAds-Service");
        ResponseWrapperAdDto responseWrapperAdDto = new ResponseWrapperAdDto();
        List<Ad> list = adRepository.findByTitleIgnoreCase(title);
        responseWrapperAdDto.setCount(list.size());
        responseWrapperAdDto.setResults(list);
        return responseWrapperAdDto;
    }

    @SneakyThrows
    public Ad addAds(CreateAdDto createAdDto, MultipartFile adsImage) {
        logger.info("Current Method is - service AddAds");
        Ad ads = adMapper.toEntity(createAdDto);
        Users user = userService.getUserById(getUserIdFromContext());
        ads.setAuthor(user);
        ads.setImage(imageService.uploadImage(adsImage));
        return adRepository.save(ads);
    }


    public FullAdDto getFullAd(long id) {
        return adMapper.toFullAdsDto(adRepository.findById((int) id).orElseThrow());
    }


    public void removeAds(int id) {
        Ad dbAd = this.adRepository.findById(id).orElseThrow(ObjectCollectedException::new);
        Image image = dbAd.getImage();
        this.imageRepository.delete(image);
        this.adRepository.delete(dbAd);
    }

    // Uses method - updateAds    controller - AdsController
    public Ad updateAds(long adId, CreateAdDto createAdDto) {
        Ad ads = getAdsById(adId);
        checkPermissionToAds(ads);
        ads.setTitle(createAdDto.getTitle());
        ads.setDescription(createAdDto.getDescription());
        ads.setPrice(createAdDto.getPrice());
        return adRepository.save(ads);
    }

    // Uses method - getAdsMe    controller - AdsController
    public Collection<Ad> getAdsMe() {
        return adRepository.findAllByAuthorId(getUserIdFromContext());
    }

    // Uses method - getComments    controller - AdsController
    public Collection<Comment> getComments(long adPk) {
        return commentRepository.findAllByAdId(adPk);
    }

    public Comment addAdsComments(long adPk, AdCommentDto adsCommentDto) {
        Comment adsComment = commentMapper.toEntity(adsCommentDto);
        Users user = userService.getUserById(getUserIdFromContext());
        adsComment.setAuthor(user);
        adsComment.setAd(getAdsById(adPk));
        adsComment.setCreatedAt(String.valueOf(Instant.now()));
        return commentRepository.save(adsComment);
    }

    public AdCommentDto getCommentsId(int ad_pk, int id) {
        Ad ad = adRepository.findById(ad_pk).orElseThrow();
        return new AdCommentDto(0, null, null);
    }

    public Comment deleteAdsComment(long adPk, long id) {
        Comment comment = getAdsComment(adPk, id);
        SecurityUtils.checkPermissionToAdsComment(comment);
        commentRepository.delete(comment);
        return comment;
    }

    // Uses method - updateComments    controller - AdsController
    public Comment updateComments(int adPk, int id, Comment commentUpdated) {
        Comment adsComment = getAdsComment(adPk, id);
        SecurityUtils.checkPermissionToAdsComment(adsComment);
        adsComment.setText(commentUpdated.getText());
        return commentRepository.save(adsComment);
    }

    // Uses method - updateAds
    public Ad getAdsById(long asId) {
        return adRepository.findById((int) asId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "The ad was not found"));
    }

    public Ad removeAdsByMe(int adId) {

        Ad ad = getAdsById(adId);
        //checkPermissionToAds(ad);
        commentRepository.deleteAdsCommentsByAdId(adId);
        adRepository.delete(ad);
        return ad;

    }

    // Uses method - getAdsComment    controller - AdsController
    public Comment getAdsComment(long adPk, long id) {

        return commentRepository.findByIdAndAdId(id, adPk)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id %d " +
                        "belonging to ad with id %d not found!", id, adPk)));

    }


    // Uses method - updateAdsImage    controller - AdsController
    @SneakyThrows
    public void updateAdsImage(long id, MultipartFile image) {
        if (image == null) {
            throw new NotFoundException("New ad image not uploaded");
        }
        Ad ads = getAdsById(id);
        checkPermissionToAds(ads);
        ads.setImage(imageService.uploadImage(image));
        adRepository.save(ads);
    }


    // Uses method - removeAds    controller - AdsController
    public Ad removeAdsById(long adId) {
        Ad ads = getAdsById(adId);
        checkPermissionToAds(ads);
        commentRepository.deleteAdsCommentsByAdId(adId);
        adRepository.delete(ads);
        return ads;
    }


}
