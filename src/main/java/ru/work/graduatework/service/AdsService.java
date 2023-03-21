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
import ru.work.graduatework.mapper.AdsMapper;
import ru.work.graduatework.repository.AdsRepository;
import ru.work.graduatework.repository.CommentRepository;
import ru.work.graduatework.repository.ImageRepository;
import ru.work.graduatework.repository.UsersRepository;

import static ru.work.graduatework.security.SecurityUtils.*;

import java.io.IOException;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class AdsService {

    private final Logger logger = LoggerFactory.getLogger(AdsService.class);


    private UsersService usersService;
    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;
    private final UsersRepository usersRepository;
    private final ImageService imageService;
    private final AdsMapper adsMapper;

    public AdsService(AdsRepository adsRepository, CommentRepository commentRepository,
                      UsersRepository usersRepository, ImageService imageService, ImageRepository imageRepository, AdsMapper adsMapper) {
        this.adsRepository = adsRepository;
        this.commentRepository = commentRepository;
        this.usersRepository = usersRepository;
        this.imageService = imageService;
        this.imageRepository = imageRepository;
        this.adsMapper = adsMapper;
    }

    // Uses method - getAllAds    controller - AdsController
    public Collection<Ads> getAllAds() {
        return adsRepository.findAll();
    }


    public ResponseWrapperAdsDto getAds(String title) {
        logger.info("Current Method is - getAds-Service");
        ResponseWrapperAdsDto responseWrapperAdsDto = new ResponseWrapperAdsDto();
        List<Ads> list = adsRepository.findByTitleIgnoreCase(title);
        responseWrapperAdsDto.setCount(list.size());
        responseWrapperAdsDto.setResults(list);
        return responseWrapperAdsDto;
    }

    @SneakyThrows
    public Ads addAds(CreateAdsDto createAdsDto, MultipartFile adsImage) {
        logger.info("Current Method is - service AddAds");
        Ads ads = adsMapper.toEntity(createAdsDto);
        Users user = usersService.getUserById(getUserIdFromContext());
        ads.setAuthor(user);
        ads.setImage(imageService.uploadImage(adsImage));
        return adsRepository.save(ads);
    }


    public FullAdsDto getFullAd(int id) {
        Users users = usersRepository.findByEmail((SecurityContextHolder
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
        Ads dbAds = this.adsRepository.findById(id).orElseThrow(ObjectCollectedException::new);
        Image image = dbAds.getImage();
        this.imageRepository.delete(image);
        this.adsRepository.delete(dbAds);
    }

    // Uses method - updateAds    controller - AdsController
    public Ads updateAds(int adId, CreateAdsDto createAdsDto) {
        Ads ads = getAdsById(adId);
        ads.setTitle(createAdsDto.getTitle());
        ads.setDescription(createAdsDto.getDescription());
        ads.setPrice(createAdsDto.getPrice());
        return adsRepository.save(ads);
    }

    // Uses method - getAdsMe    controller - AdsController
    public Collection<Ads> getAdsMe(String Email) {
        Users user = usersRepository.findByEmail(Email).orElseThrow();
        return adsRepository.findAllByAuthorId(user.getId());
    }

    // Uses method - getComments    controller - AdsController
    public Collection<Comment> getComments(long adPk) {
        return commentRepository.findAllByAdId(adPk);
    }


    public AdsCommentDto getCommentsId(int ad_pk, int id) {
        Ads ads = adsRepository.findById(ad_pk).orElseThrow();
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
        return adsRepository.findById(asId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "The ad was not found"));
    }

    public Ads removeAdsByMe(int adId) {

        Ads ads = getAdsById(adId);
        checkPermissionToAds(ads);
        commentRepository.deleteAdsCommentsByAdId(adId);
        adsRepository.delete(ads);
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
        adsRepository.save(ads);
    }


    // Uses method - removeAds    controller - AdsController
    public Ads removeAdsById(int adId) {
        Ads ads = getAdsById(adId);
        commentRepository.deleteAdsCommentsByAdId(adId);
        adsRepository.delete(ads);
        return ads;
    }



}
