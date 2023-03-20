//package ru.work.graduatework;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import ru.work.graduatework.controller.AdsController;
//import ru.work.graduatework.dto.AdsCommentDto;
//import ru.work.graduatework.dto.CreateAdsDto;
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class AdsControllerTest {
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private AdsController adsController;
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Test
//    public void contextLoads() {
//        Assertions.assertThat(adsController).isNotNull();
//    }
//
//    @Test
//    public void getAllAdsTest() {
//        Assertions
//                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/ads", String.class))
//                .isNotEmpty();
//    }
//
//    @Test
//    public void getCommentsTest() {
//        Assertions
//                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/ads" + "/{ad_pk}/comments", String.class))
//                .isNotEmpty();
//    }
//
////    @Test
////    public void addCommentsTest() {
////    }
//
//    @Test
//    public void getFullAdTest() {
//        Assertions
//                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/ads" + "/{id}", String.class))
//                .isNotEmpty();
//    }
//
//    @Test
//    public void updateAdsTest() {
//        CreateAdsDto createAdsDto = new CreateAdsDto();
//        createAdsDto.setTitle("title");
//
//        Assertions
//                .assertThat(this.restTemplate.patchForObject("http://localhost:" + port + "/ads" + "/{id}", createAdsDto, String.class))
//                .isNotNull();
//    }
//
//    @Test
//    public void getCommentsIdTest() {
//        Assertions
//                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/ads" + "/{ad_pk}/comments/{id}", String.class))
//                .isNotEmpty();
//    }
//
//    @Test
//    public void deleteCommentsIdTest() {
//        AdsCommentDto adsCommentDto = new AdsCommentDto(1, "createdAt", "text");
//        this.restTemplate.delete("http://localhost:" + port + "/ads" + "/{ad_pk}/comments/{id}" + adsCommentDto.getPk());
//    }
//
//    @Test
//    public void updateCommentsIdTest() {
//        AdsCommentDto adsCommentDto = new AdsCommentDto(1, "createdAt", "text");
//
//        Assertions
//                .assertThat(this.restTemplate.patchForObject("http://localhost:" + port + "/ads" + "/{ad_pk}/comments/{id}", adsCommentDto, String.class))
//                .isNotNull();
//    }
//
//    @Test
//    public void getAdsMeTest() {
//        Assertions
//                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/ads" + "/me", String.class))
//                .isNotEmpty();
//    }
//
//
//}
