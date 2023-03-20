//package ru.work.graduatework;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.mock.web.MockMultipartFile;
//import ru.work.graduatework.Entity.Image;
//import ru.work.graduatework.controller.UsersController;
//import ru.work.graduatework.dto.NewPasswordDto;
//
//import java.security.Principal;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class UsersControllerTest {
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private UsersController usersController;
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Test
//    public void contextLoads() {
//        Assertions.assertThat(usersController).isNotNull();
//    }
//
//    @Test
//    public void getUsersTest() {
//        Assertions
//                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/users" + "/me", String.class))
//                .isNotEmpty();
//    }
//
//    @Test
//    public void setPasswordTest() {
//        NewPasswordDto newPasswordDto = new NewPasswordDto("currentPassword", "newPassword");
//
//        Assertions
//                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/users" + "/set_password", newPasswordDto, String.class))
//                .isNotNull();
//    }
//
//    @Test
//    public void updateUserImageTest() {
//    MockMultipartFile kmlfile = new MockMultipartFile("data", "filename.kml", "text/plain", "some kml".getBytes());
//        Assertions
//                .assertThat(this.restTemplate.patchForObject("http://localhost:" + port + "/users" + "/me/image", kmlfile, String.class))
//                .isNotNull();
//    }
//
//    @Test
//    public void getUserTest() {
//        Assertions
//                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/users" + "/{id}", String.class))
//                .isNotEmpty();
//    }
//
//}
