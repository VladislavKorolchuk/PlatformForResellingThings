//package ru.work.graduatework;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.mock.web.MockMultipartFile;
//import ru.work.graduatework.controller.AdsController;
//import ru.work.graduatework.controller.ImageController;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class ImageControllerTest {
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private ImageController imageController;
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Test
//    public void contextLoads() {
//        Assertions.assertThat(imageController).isNotNull();
//    }
//
//    @Test
//    public void updateAdsImageTest() {
//        MockMultipartFile kmlfile = new MockMultipartFile("data", "filename.kml", "text/plain", "some kml".getBytes());
//
//        Assertions
//                .assertThat(this.restTemplate.patchForObject("http://localhost:" + port + "/image" + "/{id}", kmlfile, String.class))
//                .isNotNull();
//    }
//}
