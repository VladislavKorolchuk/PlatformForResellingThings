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
import ru.work.graduatework.Entity.*;
import ru.work.graduatework.controller.AdsController;
import ru.work.graduatework.dto.*;
import ru.work.graduatework.mapper.AdMapper;
import ru.work.graduatework.mapper.CommentMapper;
import ru.work.graduatework.repository.AdRepository;
import ru.work.graduatework.repository.CommentRepository;
import ru.work.graduatework.repository.ImageRepository;
import ru.work.graduatework.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collection;

@Transactional
@RequiredArgsConstructor
@Service
public class AdService {

    private final Logger logger = LoggerFactory.getLogger(AdService.class);
    private final AdRepository adRepository;
    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final AdMapper adMapper;
    private final CommentMapper commentMapper;

    /**
     * <br> Is used repository {@link UserRepository#save(Object)} </br>
     *
     * @return {@link User}
     * Uses method {@link  AdsController#getAllAds()}
     * @return {@link Collection<Ad>}
     * @author Korolchuk Vladislav
     */
    public Collection<Ad> getAllAds() {

        return adRepository.findAll();

    }

    /**
     * @param createAdDto {{@link CreateAdDto} } Input parameter
     * @param adsImage    - MultipartFile Input parameter
     * @param Email       - name user for identification Input parameter
     * @return entity  {@link Ad}
     */
    @SneakyThrows  // Feel free to throw checked exceptions
    public Ad addAds(CreateAdDto createAdDto, MultipartFile adsImage, String Email) {

        logger.info("Current Method is - addAds");
        User user = userRepository.findByEmail(Email).orElseThrow(() -> new Exception("User no found"));
        Ad ad = adMapper.toEntity(createAdDto);
        ad.setAuthor(user);
        ad.setImage(imageService.uploadImage(adsImage));
        return adRepository.save(ad);

    }

    /**
     * @param id ID Ad Input parameter
     *           <br> Is used repository {@link AdRepository#save(Object)} </br>
     *           Uses method {@link  ru.work.graduatework.controller.AdsController#getFullAd(int)}
     * @return {@link FullAdDto}
     * @author Korolchuk Vladislav
     */
    public FullAdDto getFullAd(long id) throws Exception {

        logger.info("Current Method is - getFullAd");
        return adMapper.toFullAdsDto(adRepository.findById(id).orElseThrow(() -> new Exception("Ad not found")));

    }

    /**
     * @param id ID Ad Input parameter
     *           <br> Is used entity User {@link Ad} </br
     *           <br> Is used repository {@link AdRepository#save(Object)} </br>
     *           <br> Is used repository {@link ImageRepository#save(Object)} </br>
     *           Uses method {@link  ru.work.graduatework.controller.UsersController#getUser(long)}
     * @author Korolchuk Vladislav
     */
    public void removeAds(long id) {

        logger.info("Current Method is - removeAds");
        Ad dbAd = this.adRepository.findById(id).orElseThrow(ObjectCollectedException::new);
        Image image = dbAd.getImage();
        this.imageRepository.delete(image);
        this.adRepository.delete(dbAd);
    }

    /**
     * @param adId        ID Ad Input parameter
     * @param createAdDto {@link CreateAdDto}  Input parameter
     *                    <br> Is used entity User {@link Ad} </br
     *                    <br> Is used repository {@link AdRepository#save(Object)} </br>
     *                    Uses method {@link  ru.work.graduatework.controller.AdsController#updateAds(Integer, CreateAdDto)}
     * @author Korolchuk Vladislav
     */
    public Ad updateAds(int adId, CreateAdDto createAdDto) {

        logger.info("Current Method is - updateAds");
        Ad ad = getAdsById(adId);
        ad.setTitle(createAdDto.getTitle());
        ad.setDescription(createAdDto.getDescription());
        ad.setPrice(createAdDto.getPrice());
        return adRepository.save(ad);

    }

    /**
     * @param Email name user authorizations Input parameter
     *              <br> Is used entity User {@link User} </br>
     *              <br> Is used entity User {@link UserDto} </br>
     *              <br> Is used repository {@link UserRepository#save(Object)} </br>
     *              <br> Is used repository {@link AdRepository#save(Object)} </br>
     *              Uses method {@link  AdsController#getAdsMe()}  }
     * @return {@link Collection<Ad>}
     * @author Korolchuk Vladislav
     */
    public Collection<Ad> getAdsMe(String Email) {

        logger.info("Current Method is - getAdsMe");
        User user = userRepository.findByEmail(Email).orElseThrow();
        return adRepository.findAllByAuthorId(user.getId());

    }

    /**
     * @param adPk id comment Input parameter
     *             <br> Is used repository {@link CommentRepository#save(Object)} </br>
     *             Uses method {@link  AdsController#getComments(long)}  }
     * @return {@link Collection<Comment>}
     * @author Korolchuk Vladislav
     */
    public Collection<Comment> getComments(long adPk) {

        logger.info("Current Method is - getComments");
        return commentRepository.findAllByAdId(adPk);

    }

    /**
     * @param 'userDto',currentPassword and email(name user) Input parameter
     *                                  <br> Is used entity User {@link Ad} </br>
     *                                  <br> Is used repository {@link AdRepository#save(Object)} </br>
     *                                  Uses method {@link  ru.work.graduatework.controller.AdsController#getAdsComment(long, long)}  }
     * @return {@link AdCommentDto}
     * @author Korolchuk Vladislav
     */
    public AdCommentDto getCommentsId(long ad_pk, int id) {

        logger.info("Current Method is - getCommentsId");
        Ad ad = adRepository.findById(ad_pk).orElseThrow();
        return new AdCommentDto(0, null, null);

    }

    /**
     * @param adPk           id comment Input parameter
     * @param id             ID ad Input parameter
     * @param commentUpdated {@link Comment}  Input parameter
     *                       <br> Is used entity User {@link Comment} </br>
     *                       <br> Is used repository {@link CommentRepository#save(Object)} </br>
     *                       Uses method {@link  ru.work.graduatework.controller.AdsController#updateComments(int, int, AdCommentDto)}  }
     * @return {@link Comment}
     * @author Korolchuk Vladislav
     */
    public Comment updateComments(int adPk, int id, Comment commentUpdated) {

        logger.info("Current Method is - updateComments");
        Comment comment = getAdsComment(adPk, id);
        comment.setText(commentUpdated.getText());
        return commentRepository.save(comment);

    }

    /**
     * @param asId id comment Input parameter
     *             <br> Is used repository {@link AdRepository#save(Object)} </br>
     *             Uses method {@link  ru.work.graduatework.controller.AdsController#updateAds(Integer, CreateAdDto)}  }
     * @return {@link Ad}
     * @author Korolchuk Vladislav
     */
    public Ad getAdsById(long asId) {

        logger.info("Current Method is - getAdsById");
        return adRepository.findById(asId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "The ad was not found"));

    }

    /**
     * @param adPk id comment Input parameter
     * @param id   ,id Ad Input parameter
     *             <br> Is used repository {@link CommentRepository#save(Object)} </br>
     *             Uses method {@link  ru.work.graduatework.controller.UsersController#updateUser(UserDto)}  }
     * @return {@link Comment}
     * @author Korolchuk Vladislav
     */
    public Comment getAdsComment(long adPk, long id) {

        logger.info("Current Method is - getAdsComment");
        Comment comment = commentRepository.findByIdAndAdId(id, adPk)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Ad %d " +
                                "belonging to an ad with id %d not found", id, adPk)));
        return comment;

    }

    /**
     * @param adPk id comment Input parameter
     * @param id   ,id Ad Input parameter
     *             <br> Is used entity User {@link Comment} </br>
     *             <br> Is used repository {@link CommentRepository#save(Object)} </br>
     *             Uses method {@link  ru.work.graduatework.controller.AdsController#deleteAdsComment(long, long)} (UserDto)}  }
     * @return {@link Comment}
     * @author Korolchuk Vladislav
     */
    public Comment deleteAdsComment(long adPk, long id) {

        logger.info("Current Method is - deleteAdsComment");
        Comment comment = getAdsComment(adPk, id);
        commentRepository.delete(comment);
        return comment;

    }

    /**
     * @param id    id comment Input parameter
     * @param image ,MultipartFile Input parameter
     *              <br> Is used entity User {@link Ad} </br>
     *              <br> Is used repository {@link AdRepository#save(Object)} </br>
     *              Uses method {@link  ru.work.graduatework.controller.AdsController#updateAdsImage(int, MultipartFile)} (int, int, AdCommentDto)} (long, long)} (UserDto)}  }
     * @author Korolchuk Vladislav
     */
    @SneakyThrows
    public void updateAdsImage(int id, MultipartFile image) {

        logger.info("Current Method is - updateAdsImage");
        Ad ad = getAdsById(id);
        imageRepository.delete(ad.getImage());
        ad.setImage(imageService.uploadImage(image));
        adRepository.save(ad);

    }


    /**
     * @param adId id Ad Input parameter
     *             <br> Is used entity User {@link Ad} </br>
     *             <br> Is used repository {@link CommentRepository#save(Object)} </br>
     *             <br> Is used repository {@link AdRepository#save(Object)} </br>
     *             Uses method {@link  ru.work.graduatework.controller.AdsController#removeAds(int)} (UserDto)}  }
     * @return {@link Ad}
     * @author Korolchuk Vladislav
     */
    public Ad removeAdsById(int adId) {

        logger.info("Current Method is - removeAdsById");
        Ad ad = getAdsById(adId);
        commentRepository.deleteAdsCommentsByAdId(adId);
        adRepository.delete(ad);
        return ad;

    }

    /**
     * @param adPk         id comment Input parameter
     * @param adCommentDto {@link AdCommentDto}  Input parameter
     * @param Email        - name user for identification Input parameter
     *                     <br> Is used entity User {@link User} </br>
     *                     <br> Is used entity User {@link Comment} </br>
     *                     <br> Is used repository {@link UserRepository#save(Object)} </br>
     *                     <br> Is used repository {@link AdRepository#save(Object)} </br>
     *                     Uses method {@link  ru.work.graduatework.controller.AdsController#addAdsComment(int, AdCommentDto)} (UserDto)}  }
     * @return {@link Comment}
     * @author Korolchuk Vladislav
     */
    public Comment addAdsComment(long adPk, AdCommentDto adCommentDto, String Email) throws Exception {

        logger.info("Current Method is - addAdsComment");
        User user = userRepository.findByEmail(Email).orElseThrow(() -> new Exception("User not found"));
        Comment comment = commentMapper.toEntity(adCommentDto);
        comment.setAuthor(user);
        comment.setAd(adRepository.findById(adPk).orElseThrow(() -> new Exception("Ad not found")));
        comment.setCreatedAt(String.valueOf(Instant.now()));
        return commentRepository.save(comment);

    }

}
