//package ru.work.graduatework;
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
//import ru.work.graduatework.Entity.User;
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
//    User users = new User();
//    Integer author = 1;
//    Integer pk = 1;
//    Integer price = 1;
//    String title = "1";
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
////    @Test
////    public void addAdsTest() throws IOException {
////
////        imageRepository.save(image);
////
////        AdDto request = new AdDto(author, description, image, pk, price, title);
////        CreateAdDto createAdsDto = new CreateAdDto(description, price, title);
////        AdDto result = adsService.addAds(createAdsDto, kmlfile);
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
//        FullAdDto expected = new FullAdDto();
//        expected.setAuthorFirstName(users.getFirstName());
//        expected.setAuthorLastName(users.getLastName());
//        //  fullAdsDto.setDescription(ads.getDescription());
//        expected.setEmail(users.getEmail());
//        //  fullAdsDto.setPrice(ads.getPrice());
//        //  fullAdsDto.setTitle(ads.getTitle());
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
////    @Test
////    public void updateAdsTest() {
////
////    }
//
//    @Test
//    public void getAdsMe() {
//        Collection<Ad> expected = adsRepository.findAll();
//        Collection<Ad> actual = adsService.getAdsMe();
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
//        Ad expected = adsRepository.findById(pk).orElseThrow();
//        Ad actual = adsService.getAdsById(pk);
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