package ru.work.graduatework.service;

import com.sun.jdi.ObjectCollectedException;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.work.graduatework.Entity.*;
import ru.work.graduatework.dto.*;
import ru.work.graduatework.mapper.AdMapper;
import ru.work.graduatework.repository.AdRepository;
import ru.work.graduatework.repository.CommentRepository;
import ru.work.graduatework.repository.ImageRepository;
import ru.work.graduatework.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Transactional
@Service
public class AdService {

    private final Logger logger = LoggerFactory.getLogger(AdService.class);
    private UserService userService;
    private final AdRepository adRepository;
    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final AdMapper adMapper;

    public AdService(AdRepository adRepository, CommentRepository commentRepository,
                     UserRepository userRepository, ImageService imageService, ImageRepository imageRepository, AdMapper adMapper) {
        this.adRepository = adRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.imageRepository = imageRepository;
        this.adMapper = adMapper;
    }

    // Uses method - getAllAds    controller - AdsController
    public Collection<Ad> getAllAds() {
        return adRepository.findAll();
    }


//    public ResponseWrapperAdDto getAds(String title) {
//        logger.info("Current Method is - getAds-Service");
//        ResponseWrapperAdDto responseWrapperAdDto = new ResponseWrapperAdDto();
//        List<Ad> list = adRepository.findByTitleIgnoreCase(title);
//        responseWrapperAdDto.setCount(list.size());
//        responseWrapperAdDto.setResults(list);
//        return responseWrapperAdDto;
//    }

    @SneakyThrows
    public Ad addAds(CreateAdDto createAdDto, MultipartFile adsImage, String Email) {
        logger.info("Current Method is - service AddAds");
        User user = userRepository.findByEmail(Email).orElseThrow();
        Ad ad = adMapper.toEntity(createAdDto);
        ad.setAuthor(user);
        ad.setImage(imageService.uploadImage(adsImage));
        return adRepository.save(ad);
    }


    public FullAdDto getFullAd(int id) {
        return adMapper.toFullAdsDto(adRepository.findById(id).orElseThrow());
    }


    public void removeAds(int id) {
        Ad dbAd = this.adRepository.findById(id).orElseThrow(ObjectCollectedException::new);
        Image image = dbAd.getImage();
        this.imageRepository.delete(image);
        this.adRepository.delete(dbAd);
    }

    // Uses method - updateAds    controller - AdsController
    public Ad updateAds(int adId, CreateAdDto createAdDto) {
        Ad ad = getAdsById(adId);
        ad.setTitle(createAdDto.getTitle());
        ad.setDescription(createAdDto.getDescription());
        ad.setPrice(createAdDto.getPrice());
        return adRepository.save(ad);
    }

    // Uses method - getAdsMe    controller - AdsController
    public Collection<Ad> getAdsMe(String Email) {
         User user = userRepository.findByEmail(Email).orElseThrow();
         return adRepository.findAllByAuthorId(user.getId());
    }

    // Uses method - getComments    controller - AdsController
    public Collection<Comment> getComments(long adPk) {
        return commentRepository.findAllByAdId(adPk);
    }


    public AdCommentDto getCommentsId(int ad_pk, int id) {
        Ad ad = adRepository.findById(ad_pk).orElseThrow();
        return new AdCommentDto(0, null, null);
    }

    public void deleteCommentsId() {
    }

    // Uses method - updateComments    controller - AdsController
    public Comment updateComments(int adPk, int id, Comment commentUpdated) {

        Comment comment = getAdsComment(adPk, id);
        comment.setText(commentUpdated.getText());
        return commentRepository.save(comment);

    }

    // Uses method - updateAds
    public Ad getAdsById(int asId) {
        return adRepository.findById(asId).orElseThrow(
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

        Comment comment = commentRepository.findByIdAndAdId(id, adPk)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Ad %d " +
                                "belonging to an ad with id %d not found", id, adPk)));
        return comment;

    }

    // Uses method - deleteAdsComment    controller - AdsController
    public Comment deleteAdsComment(long adPk, long id) {
        Comment comment = getAdsComment(adPk, id);
        commentRepository.delete(comment);
        return comment;
    }

    // Uses method - updateAdsImage    controller - AdsController
    @SneakyThrows
    public void updateAdsImage(int id, MultipartFile image) {
        Ad ad = getAdsById(id);

        ad.setImage(imageService.uploadImage(image));
        adRepository.save(ad);
    }


    // Uses method - removeAds    controller - AdsController
    public Ad removeAdsById(int adId) {
        Ad ad = getAdsById(adId);
        commentRepository.deleteAdsCommentsByAdId(adId);
        adRepository.delete(ad);
        return ad;
    }


}
