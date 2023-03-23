//package ru.work.graduatework;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import ru.work.graduatework.Entity.Ad;
//import ru.work.graduatework.Entity.Image;
//import ru.work.graduatework.repository.AdRepository;
//import ru.work.graduatework.repository.ImageRepository;
//import ru.work.graduatework.repository.UserRepository;
//import ru.work.graduatework.service.ImageService;
//
//import java.io.IOException;
//import java.nio.file.Path;
//import java.util.Objects;
//
//@SpringBootTest
//public class ImageServiceTest {
//    @Autowired
//    AdRepository adsRepository;
//    @Autowired
//    ImageRepository imageRepository;
//    @Autowired
//    UserRepository usersRepository;
//    @Autowired
//    ImageService imageService;
//    @Value("${path.to.image.folder}")
//    private String imageDir;
//    Image image = new Image();
//    //    byte[] fileContent = new byte[10];
//    MockMultipartFile kmlfile = new MockMultipartFile("data", "filename.kml", "text/plain", "some kml".getBytes());
//
//    @BeforeEach
//    public void setUp() {
//        imageRepository.save(image);
//    }
//
//    @AfterEach
//    public void postUp() {
//        imageRepository.deleteAll();
//    }
//
//    @Test
//    public void addAdsImageTest() throws IOException {
//        Ad ads = new Ad();
//        ads.setImage(image);
//        adsRepository.save(ads);
//
//        Path path = Path.of(imageDir, ads.getId() + "." + "kml");
//        image.setFilePath(path.toString());
//        image.setMediaType(kmlfile.getContentType());
//        image.setFileSize(kmlfile.getSize());
//        imageRepository.save(image);
//
//        Image expected = image;
//        Image actual = imageService.addAdsImage(ads, kmlfile);
//
//        Assertions
//                .assertThat(expected).isEqualTo(actual);
//
//        adsRepository.deleteAll();
//    }
//
////    @Test
////    public void uploadImageTest() {
////    }
//}
