//package ru.work.graduatework.serviceTest;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import ru.work.graduatework.Entity.Ad;
//import ru.work.graduatework.Entity.Comment;
//import ru.work.graduatework.Entity.Image;
//import ru.work.graduatework.Entity.Users;
//import ru.work.graduatework.dto.*;
//import ru.work.graduatework.repository.AdRepository;
//import ru.work.graduatework.repository.CommentRepository;
//import ru.work.graduatework.repository.ImageRepository;
//import ru.work.graduatework.mapper.CommentMapper;
//import ru.work.graduatework.repository.UserRepository;
//import ru.work.graduatework.service.AdService;
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
//    public AdService adsService;
//    @Autowired
//    public ImageService imageService;
//    Ad ads = new Ad();
//    Users users = new Users();
//    Integer author = 1;
//    Integer pk = 1;
//    Integer price = 1;
//    String title = "1";
//    String email = "email@email";
//    Image image = new Image();
//    byte[] fileContent = new byte[10];
//    MockMultipartFile kmlfile = new MockMultipartFile("data", "filename.kml", "text/plain", "some kml".getBytes());
//    List<Image> images = new ArrayList<>();
//    Collection<Comment> commentCollection = new HashSet<>();
//    Collection<Ad> adsCollection = new HashSet<>();
//    String description = "1";
//
//    @BeforeEach
//    public void setUp() {
//        users.setEmail(email);
//        usersRepository.save(users);
//        imageRepository.save(image);
//        ads.setAuthor(users);
//        ads.setPrice(price);
//        ads.setTitle(title);
//        ads.setImage(image);
//        ads.setDescription(description);
//
//        adsRepository.save(ads);
//    }
//
//    @AfterEach
//    public void postUp() {
//        adsRepository.deleteAll();
//        usersRepository.deleteAll();
//        imageRepository.deleteAll();
//    }
//
//    @Test
//    public void getAllAdsTest() {
//        Collection<Ad> ads = this.adsService.getAllAds();
//        Assertions
//                .assertThat(ads).isNotEmpty();
//    }
//
//    @Test
//    public void getAdsTest() {
//        List<Ad> list = adsRepository.findByTitleIgnoreCase(title);
//        ResponseWrapperAdDto expected = new ResponseWrapperAdDto();
//        expected.setCount(list.size());
//        expected.setResults(list);
//
//        ResponseWrapperAdDto actual = adsService.getAds(title);
//        Assertions
//                .assertThat(actual).isEqualTo(expected);
//    }
//
//    @Test
//    public void addAdsTest()  {
//
//        CreateAdDto createAdsDto = new CreateAdDto();
//        createAdsDto.setDescription(description);
//        createAdsDto.setPrice(price);
//        createAdsDto.setTitle(title);
//
//        Ad result = adsService.addAds(createAdsDto, kmlfile, email);
//
//        Assertions
//                .assertThat(ads.getAuthor()).isEqualTo(result.getAuthor());
//        Assertions
//                .assertThat(ads.getDescription()).isEqualTo(result.getDescription());
//        Assertions
//                .assertThat(ads.getPrice()).isEqualTo(result.getPrice());
//        Assertions
//                .assertThat(ads.getTitle()).isEqualTo(result.getTitle());
//
//    }
//
//    @Test
//    public void getFullAdTest() {
//        FullAdDto expected = new FullAdDto();
//        expected.setAuthorFirstName(users.getFirstName());
//        expected.setAuthorLastName(users.getLastName());
//        expected.setEmail(users.getEmail());
//
//        FullAdDto actual = adsService.getFullAd(pk);
//        Assertions
//                .assertThat(actual).isEqualTo(expected);
//    }
////    @Test
////    public void removeAdsTest() {
////        CreateAdDto createAdsDto2 = new CreateAdDto("description", 2, "title");
////        AdDto part2 = adsService.addAds(createAdsDto2, kmlfile);
////        adsService.removeAds(part2.getPk());
////        ResponseWrapperAdDto adsDto = this.adsService.getAllAds();
////
////        Assertions
////                .assertThat(adsDto.getResults()).hasSize(1);
////        imageRepository.deleteAll();
////    }
//
//    @Test
//    public void updateAdsTest() {
//        CreateAdDto createAdsDto = new CreateAdDto();
//        createAdsDto.setDescription("2");
//        createAdsDto.setPrice(2);
//        createAdsDto.setTitle("2");
//
//        Ad actual = adsService.updateAds(ads.getId(), createAdsDto);
//
//        Assertions
//                .assertThat(actual.getDescription()).isEqualTo("2");
//        Assertions
//                .assertThat(actual.getPrice()).isEqualTo(2);
//        Assertions
//                .assertThat(actual.getTitle()).isEqualTo("2");
//    }
//
//    @Test
//    public void getAdsMe() {
//        Collection<Ad> expected = adsRepository.findAll();
//        Collection<Ad> actual = adsService.getAdsMe(users.getEmail());
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
//        Collection<Ad> expected = adsRepository.findAll();
//        Ad actual = adsService.getAdsById(ads.getId());
//        Assertions
//                .assertThat(expected).hasSize(1);
//        Assertions
//                .assertThat(expected).contains(actual);
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
////        AdCommentDto adsCommentDto = new AdCommentDto(author, "createdAt", pk, "Text");
////
////        Comment comment = CommentMapper.toEntity(adsCommentDto);
////        ads.getCommentCollection().add(comment);
////        commentRepository.save(comment);
////
////        AdCommentDto actual = adsServiceImpl.addComments(pk, adsCommentDto);
////        Assertions
////                .assertThat(adsCommentDto).isEqualTo(actual);
////
////        commentRepository.delete(comment);
////    }
//}