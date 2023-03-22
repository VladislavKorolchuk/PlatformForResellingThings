package ru.work.graduatework.service;

import com.sun.jdi.ObjectCollectedException;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
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

import java.util.Collection;
import java.util.List;

@Service
public class AdsService {

    private final Logger logger = LoggerFactory.getLogger(AdsService.class);


    private UsersService usersService;
    private final AdRepository adRepository;
    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final AdMapper adMapper;

    public AdsService(AdRepository adRepository, CommentRepository commentRepository,
                      UserRepository userRepository, ImageService imageService, ImageRepository imageRepository, AdMapper adMapper) {
        this.adRepository = adRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.imageRepository = imageRepository;
        this.adMapper = adMapper;
    }

    // Uses method - getAllAds    controller - AdsController
    public Collection<Ads> getAllAds() {
        return adRepository.findAll();
    }


    public ResponseWrapperAdsDto getAds(String title) {
        logger.info("Current Method is - getAds-Service");
        ResponseWrapperAdsDto responseWrapperAdsDto = new ResponseWrapperAdsDto();
        List<Ads> list = adRepository.findByTitleIgnoreCase(title);
        responseWrapperAdsDto.setCount(list.size());
        responseWrapperAdsDto.setResults(list);
        return responseWrapperAdsDto;
    }

    @SneakyThrows
    public Ads addAds(CreateAdsDto createAdsDto, MultipartFile adsImage, String Email) {
        logger.info("Current Method is - service AddAds");
        Users user = userRepository.findByEmail(Email).orElseThrow();
        Ads ads = adMapper.toEntity(createAdsDto);
        ads.setAuthor(user);
        ads.setImage(imageService.uploadImage(adsImage));
        return adRepository.save(ads);
    }


    public FullAdsDto getFullAd(int id) {
        Users users = userRepository.findByEmail((SecurityContextHolder
                .getContext().getAuthentication().getName())).orElseThrow();
        //  Ads ads = adsRepository.findById(id).orElseThrow();
        FullAdsDto fullAdsDto = new FullAdsDto();
        fullAdsDto.setAuthorFirstName(users.getFirstName());
        fullAdsDto.setAuthorLastName(users.getLastName());
        //  fullAdsDto.setDescription(ads.getDescription());
        fullAdsDto.setEmail(users.getEmail());
        //  fullAdsDto.setPrice(ads.getPrice());
        //  fullAdsDto.setTitle(ads.getTitle());
        return fullAdsDto;
    }


    public void removeAds(int id) {
        Ads dbAds = this.adRepository.findById(id).orElseThrow(ObjectCollectedException::new);
        Image image = dbAds.getImage();
        this.imageRepository.delete(image);
        this.adRepository.delete(dbAds);
    }

    // Uses method - updateAds    controller - AdsController
    public Ads updateAds(int adId, CreateAdsDto createAdsDto) {
        Ads ads = getAdsById(adId);
        ads.setTitle(createAdsDto.getTitle());
        ads.setDescription(createAdsDto.getDescription());
        ads.setPrice(createAdsDto.getPrice());
        return adRepository.save(ads);
    }

    // Uses method - getAdsMe    controller - AdsController
    public Collection<Ads> getAdsMe(String Email) {
        Users user = userRepository.findByEmail(Email).orElseThrow();
        return adRepository.findAllByAuthorId(user.getId());
    }

    // Uses method - getComments    controller - AdsController
    public Collection<Comment> getComments(long adPk) {
        return commentRepository.findAllByAdId(adPk);
    }


    public AdsCommentDto getCommentsId(int ad_pk, int id) {
        Ads ads = adRepository.findById(ad_pk).orElseThrow();
        return new AdsCommentDto(0, null, null);
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
    public Ads getAdsById(int asId) {
        return adRepository.findById(asId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "The ad was not found"));
    }

    public Ads removeAdsByMe(int adId) {

        Ads ads = getAdsById(adId);
        //checkPermissionToAds(ads);
        commentRepository.deleteAdsCommentsByAdId(adId);
        adRepository.delete(ads);
        return ads;

    }

    // Uses method - getAdsComment    controller - AdsController
    public Comment getAdsComment(long adPk, long id) {

        Comment comment = commentRepository.findByIdAndAdId(id, adPk)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Ads %d " +
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
    public void updateAdsImage (int id, MultipartFile image){
        Ads ads = getAdsById(id);

        ads.setImage(imageService.uploadImage(image));
        adRepository.save(ads);
    }


    // Uses method - removeAds    controller - AdsController
    public Ads removeAdsById(int adId) {
        Ads ads = getAdsById(adId);
        commentRepository.deleteAdsCommentsByAdId(adId);
        adRepository.delete(ads);
        return ads;
    }



}
