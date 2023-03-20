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
import ru.work.graduatework.mapper.AdsCommentMapper;
import ru.work.graduatework.mapper.AdsMapper;
import ru.work.graduatework.repository.AdsRepository;
import ru.work.graduatework.repository.CommentRepository;
import ru.work.graduatework.repository.UsersRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Transactional
@Service
public class AdsService {

    private final Logger logger = LoggerFactory.getLogger(AdsService.class);

    private final UsersService userService;
    private final ImageService imageService;
    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;
    private final AdsMapper adsMapper;
    private final AdsCommentMapper adsCommentMapper;
    private final UsersRepository usersRepository;


    public AdsService(UsersService userService, ImageService imageService, AdsRepository adsRepository, CommentRepository adsCommentRepository, CommentRepository commentRepository, AdsMapper adsMapper, AdsCommentMapper adsCommentMapper, UsersRepository usersRepository) {
        this.userService = userService;
        this.imageService = imageService;
        this.adsRepository = adsRepository;
        this.commentRepository = commentRepository;
        this.adsMapper = adsMapper;
        this.adsCommentMapper = adsCommentMapper;
        this.usersRepository = usersRepository;
    }

    public Collection<Ads> getAllAds() {
        return adsRepository.findAll();
    }


    @SneakyThrows
    @Transactional
    public Ads addAds(CreateAdsDto createAdsDto, MultipartFile adsImage) {
        Ads ads = adsMapper.toEntity(createAdsDto);
        Users users = usersRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(EntityExistsException::new);
        ads.setAuthor(users);
        ads.setImage(imageService.uploadImage(adsImage));
        return adsRepository.save(ads);
    }

    public List<AdsDto> getAdsMe() {
        Users user = usersRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();
        Collection<Ads> adsList = adsRepository.findAllByAuthorId(user.getId());
        return adsMapper.toDto(adsList);
    }


    public Ads getAdsById(Long adId) {
        return adsRepository.findById(adId).orElseThrow(EntityNotFoundException::new);
    }


    public void removeAds(long id) {
        Ads dbAds = this.adsRepository.findById(id).orElseThrow(ObjectCollectedException::new);
        commentRepository.deleteAdsCommentsByAdId(id);
        this.adsRepository.delete(dbAds);
    }

    public Comment getAdsComment(long adPk, long id) {
        Comment comment = commentRepository.findByIdAndAdId(id, adPk)
                .orElseThrow(ObjectCollectedException::new);
        return comment;
    }


    public Collection<Comment> getComments(long adPk) {
        return commentRepository.findAllByAdId(adPk);
    }

    public Comment addAdsComments(long adPk, CommentDto commentDto) {
        Comment adsComment = adsCommentMapper.toEntity(commentDto);
        Users user = usersRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow(EntityExistsException::new);
        adsComment.setAuthor(user);
        adsComment.setAd(getAdsById(adPk));
        adsComment.setCreatedAt(userService.dateUserRegistration());
        return commentRepository.save(adsComment);
    }


    public Comment deleteAdsComment(long adPk, long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        commentRepository.delete(comment);
        return comment;
    }

    public Comment updateComments(int adPk, int id, Comment commentUpdated) {
        Comment adsComment = getAdsComment(adPk, id);
        adsComment.setText(commentUpdated.getText());
        return commentRepository.save(adsComment);
    }

    @SneakyThrows
    public void updateAdsImage(long id, MultipartFile image) {
        Ads ads = getAdsById(id);
        ads.setImage(imageService.uploadImage(image));
        adsRepository.save(ads);
    }

    public Ads updateAds(Long adId, CreateAdsDto createAdsDto) {
        Ads ads = getAdsById(adId);
        ads.setTitle(createAdsDto.getTitle());
        ads.setDescription(createAdsDto.getDescription());
        ads.setPrice(createAdsDto.getPrice());
        return adsRepository.save(ads);
    }
}
