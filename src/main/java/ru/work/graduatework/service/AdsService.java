package ru.work.graduatework.service;

import com.sun.jdi.ObjectCollectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.*;
import ru.work.graduatework.dto.*;
import ru.work.graduatework.mapper.AdsMapper;
import ru.work.graduatework.repository.AdsRepository;
import ru.work.graduatework.repository.CommentRepository;
import ru.work.graduatework.repository.ImageRepository;
import ru.work.graduatework.repository.UsersRepository;
import ru.work.graduatework.mapper.AdsMapper1;
import ru.work.graduatework.mapper.CommentMapper;

import java.io.IOException;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdsService {

    private final Logger logger = LoggerFactory.getLogger(AdsService.class);

    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;
    private final ImageRepository imageRepository;
    private final UsersRepository usersRepository;
    private final ImageService imageService;

    public AdsService(AdsRepository adsRepository, CommentRepository commentRepository,
                      UsersRepository usersRepository, ImageService imageService, ImageRepository imageRepository) {
        this.adsRepository = adsRepository;
        this.commentRepository = commentRepository;
        this.usersRepository = usersRepository;
        this.imageService = imageService;
        this.imageRepository = imageRepository;
    }


    public Collection<Ads> getAllAds() {
        return adsRepository.findAll();
//        logger.info("Current Method is - getAllAds-Service");
//        ResponseWrapperAdsDto responseWrapperAdsDto = new ResponseWrapperAdsDto();
//        List<Ads> dtoList = adsRepository.findAll();
//        responseWrapperAdsDto.setCount(dtoList.size());
//        responseWrapperAdsDto.setResults(dtoList);
//        return responseWrapperAdsDto;
//        return adsRepository.findAll().stream().map(AdsMapper::toDto).collect(Collectors.toList());
    }


    public ResponseWrapperAdsDto getAds(String title) {
        logger.info("Current Method is - getAds-Service");
        ResponseWrapperAdsDto responseWrapperAdsDto = new ResponseWrapperAdsDto();
        List<Ads> list = adsRepository.findByTitleIgnoreCase(title);
        responseWrapperAdsDto.setCount(list.size());
        responseWrapperAdsDto.setResults(list);
        return responseWrapperAdsDto;
    }

    // TODO: добавлять пользователя

    @Transactional
    public AdsDto addAds(CreateAdsDto createAdsDto, MultipartFile adsImage) {
        logger.info("Current Method is - serviceAddAds");
//        Users users1 = usersRepository.findByEmail((SecurityContextHolder
//                .getContext().getAuthentication().getName())).orElseThrow();  //не свагер

        Users users = new Users();        //свагер
        usersRepository.save(users);      //свагер
        Users users1 = usersRepository.findById(1).orElseThrow();  //ещё свагер
        Ads ads = new Ads();
        ads.setTitle(createAdsDto.getTitle());
        ads.setPrice(createAdsDto.getPrice());
        ads.setDescription(createAdsDto.getDescription());
        ads.setUser(users1);
        adsRepository.save(ads);
        try {
            Image image = imageService.addAdsImage(ads.getPk(), adsImage);
            ads.setImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return AdsMapper1.toDto(ads);
    }



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


    public void removeAds(int id) {
        Ads dbAds = this.adsRepository.findById(id).orElseThrow(ObjectCollectedException::new);
        Image image = dbAds.getImage();
        this.imageRepository.delete(image);
        this.adsRepository.delete(dbAds);
    }


    public AdsDto updateAds(int id, CreateAdsDto createAdsDto) {
        Ads ads = adsRepository.findById(id).orElseThrow((ObjectCollectedException::new));
        ads.setTitle(createAdsDto.getTitle());
        ads.setDescription(createAdsDto.getDescription());
        ads.setPrice(createAdsDto.getPrice());
        return AdsMapper1.toDto(adsRepository.save(ads));
    }


    public ResponseWrapperAdsDto getAdsMe() {
        Users user = usersRepository.findByEmail((SecurityContextHolder.
                getContext().getAuthentication().getName())).orElseThrow();
//        List<Ads> adsList = new ArrayList<>(user.getAdsCollection()); убрал отношение
        ResponseWrapperAdsDto responseWrapperAdsDto = new ResponseWrapperAdsDto();
//        responseWrapperAdsDto.setCount(adsList.size());
//        responseWrapperAdsDto.setResults(adsList);
        return responseWrapperAdsDto;
    }


    public ResponseWrapperCommentDto getComments(Integer ad_pk) {
        ResponseWrapperCommentDto responseWrapperCommentDto = new ResponseWrapperCommentDto();
        Ads ads = adsRepository.findById(ad_pk).orElseThrow();
//        List<Comment> dtoList = new ArrayList<>(ads.getCommentCollection()); убрал отношение
//        responseWrapperCommentDto.setCount(dtoList.size());
//        responseWrapperCommentDto.setResults(dtoList);
        return responseWrapperCommentDto;
    }


    @Transactional
    public CommentDto addComments(int ad_pk, CommentDto commentDto) {
        logger.info("Current Method is - addCommentsService");
        Ads ads = this.adsRepository.findById(ad_pk).orElseThrow();
        Comment comment = CommentMapper.toEntity(commentDto);
//        ads.getCommentCollection().add(comment);
        return CommentMapper.toDto(commentRepository.save(comment));
    }


    public CommentDto getCommentsId(Integer ad_pk, Integer id) {
        Ads ads = adsRepository.findById(ad_pk).orElseThrow();
        return new CommentDto(null, null, null);
    }


    public void deleteCommentsId() {
    }


    public CommentDto updateCommentsId() {
        return null;
    }
}
