//package ru.work.graduatework;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import ru.work.graduatework.Entity.Ads;
//import ru.work.graduatework.Entity.Comment;
//import ru.work.graduatework.Entity.Image;
//import ru.work.graduatework.Entity.Users;
//import ru.work.graduatework.dto.*;
//import ru.work.graduatework.repository.AdRepository;
//import ru.work.graduatework.repository.CommentRepository;
//import ru.work.graduatework.repository.ImageRepository;
//import ru.work.graduatework.mapper.CommentMapper;
//import ru.work.graduatework.repository.UserRepository;
//import ru.work.graduatework.service.AdsService;
//import ru.work.graduatework.service.ImageService;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.List;
//
//@SpringBootTest
//public class AdsServiceTest {
//    @Autowired
//    public AdRepository adsRepository;
//    @Autowired
//    public CommentRepository commentRepository;
//    @Autowired
//    public ImageRepository imageRepository;
//    @Autowired
//    public UserRepository usersRepository;
//    @Autowired
//    public AdsService adsService;
//    @Autowired
//    public ImageService imageService;
//    Ads ads = new Ads();
//    Users users = new Users();
//    Integer author = 1;
//    Integer pk = 1;
//    Integer price = 1;
//    String title = "1";
//    Image image = new Image();
//    byte[] fileContent = new byte[10];
//    MockMultipartFile kmlfile = new MockMultipartFile("data", "filename.kml", "text/plain", "some kml".getBytes());
//    List<Image> images = new ArrayList<>();
//    Collection<Comment> commentCollection = new HashSet<>();
//    Collection<Ads> adsCollection = new HashSet<>();
//    String description = "1";
//
//    @BeforeEach
//    public void setUp() {
//        ads.setAuthor(users);
//        ads.setPrice(price);
//        ads.setTitle(title);
//        ads.setImage(ads.getImage());
//        ads.setDescription(description);
//
//        adsRepository.save(ads);
//    }
//
//    @AfterEach
//    public void postUp() {
//        adsRepository.deleteAll();
//    }
//
//    @Test
//    public void getAllAdsTest() {
//        Collection<Ads> ads = this.adsService.getAllAds();
//        Assertions
//                .assertThat(ads).isNotEmpty();
//    }
//
//    @Test
//    public void getAdsTest() {
//        List<Ads> list = adsRepository.findByTitleIgnoreCase(title);
//        ResponseWrapperAdsDto expected = new ResponseWrapperAdsDto();
//        expected.setCount(list.size());
//        expected.setResults(list);
//
//        ResponseWrapperAdsDto actual = adsService.getAds(title);
//        Assertions
//                .assertThat(actual).isEqualTo(expected);
//    }
//
////    @Test
////    public void addAdsTest() throws IOException {
////
////        imageRepository.save(image);
////
////        AdsDto request = new AdsDto(author, description, image, pk, price, title);
////        CreateAdsDto createAdsDto = new CreateAdsDto(description, price, title);
////        AdsDto result = adsService.addAds(createAdsDto, kmlfile);
////
////        Assertions
////                .assertThat(request.getAuthor()).isEqualTo(result.getAuthor());
////        Assertions
////                .assertThat(request.getDescription()).isEqualTo(result.getDescription());
////        Assertions
////                .assertThat(request.getPrice()).isEqualTo(result.getPrice());
////        Assertions
////                .assertThat(request.getTitle()).isEqualTo(result.getTitle());
////        imageRepository.deleteAll();
////    }
//
//    @Test
//    public void getFullAdTest() {
//        FullAdsDto expected = new FullAdsDto();
//        expected.setAuthorFirstName(users.getFirstName());
//        expected.setAuthorLastName(users.getLastName());
//        //  fullAdsDto.setDescription(ads.getDescription());
//        expected.setEmail(users.getEmail());
//        //  fullAdsDto.setPrice(ads.getPrice());
//        //  fullAdsDto.setTitle(ads.getTitle());
//
//        FullAdsDto actual = adsService.getFullAd(pk);
//        Assertions
//                .assertThat(actual).isEqualTo(expected);
//    }
////    @Test
////    public void removeAdsTest() {
////        CreateAdsDto createAdsDto2 = new CreateAdsDto("description", 2, "title");
////        AdsDto part2 = adsService.addAds(createAdsDto2, kmlfile);
////        adsService.removeAds(part2.getPk());
////        ResponseWrapperAdsDto adsDto = this.adsService.getAllAds();
////
////        Assertions
////                .assertThat(adsDto.getResults()).hasSize(1);
////        imageRepository.deleteAll();
////    }
//
////    @Test
////    public void updateAdsTest() {
////
////    }
//
//    @Test
//    public void getAdsMe() {
//        Collection<Ads> expected = adsRepository.findAll();
//        Collection<Ads> actual = adsService.getAdsMe();
//        Assertions
//                .assertThat(expected).isEqualTo(actual);
//    }
//
////    @Test
////    public void getCommentsTest() {
////    }
//
////    @Test
////    public void getCommentsIdTest() {
////    }
////
////    @Test
////    public void deleteCommentsIdTest() {
////    }
//
////    @Test
////    public void updateCommentsIdTest() {
////    }
//
//    @Test
//    public void getAdsByID() {
//        Ads expected = adsRepository.findById(pk).orElseThrow();
//        Ads actual = adsService.getAdsById(pk);
//        Assertions
//                .assertThat(actual).isEqualTo(expected);
//    }
//
////    @Test
////    public void removeAdsByMeTest() {
////    }
//
//    @Test
//    public void getAdsComment() {
//        Comment expected = new Comment(pk, "createdAT", "text", users, ads);
//        Comment actual = adsService.getAdsComment(pk, pk);
//
//        Assertions
//                .assertThat(actual).isEqualTo(expected);
//        commentRepository.deleteAll();
//    }
//
////    @Test
////    public void addAdsCommentsTest() {
////    }
//
////    СОВСЕМ УДАЛЕН??
////    @Test
////    public void addCommentsTest() {
////        AdsCommentDto adsCommentDto = new AdsCommentDto(author, "createdAt", pk, "Text");
////
////        Comment comment = CommentMapper.toEntity(adsCommentDto);
////        ads.getCommentCollection().add(comment);
////        commentRepository.save(comment);
////
////        AdsCommentDto actual = adsServiceImpl.addComments(pk, adsCommentDto);
////        Assertions
////                .assertThat(adsCommentDto).isEqualTo(actual);
////
////        commentRepository.delete(comment);
////    }
//}