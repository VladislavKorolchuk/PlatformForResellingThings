//package ru.work.graduatework;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import ru.work.graduatework.Entity.Image;
//import ru.work.graduatework.Entity.User;
//import ru.work.graduatework.repository.ImageRepository;
//import ru.work.graduatework.repository.UserRepository;
//import ru.work.graduatework.service.ImageService;
//import ru.work.graduatework.service.UserService;
//
//import java.util.Collection;
//import java.util.Optional;
//
//@SpringBootTest
//public class UserServiceTest {
//    @Autowired
//    UserRepository usersRepository;
//    @Autowired
//    ImageRepository imageRepository;
//    @Autowired
//    UserService usersService;
//    @Autowired
//    ImageService imageService;
//
//    User users = new User();
//    String email = "email";
//
//
////    Image image = new Image();
////    MockMultipartFile kmlfile = new MockMultipartFile("data", "filename.kml", "text/plain", "some kml".getBytes());
//
//    @BeforeEach
//    public void setUp() {
//        users.setEmail(email);
//        usersRepository.save(users);
//    }
//
//    @AfterEach
//    public void postUp() {
//        usersRepository.deleteAll();
//    }
//
//    @Test
//    public void getUsersTest() {
//        Collection<User> expected = usersRepository.findAll();
//        User actual = usersService.getUsers();
//
//        Assertions
//                .assertThat(expected).hasSize(1);
//       Assertions
//                .assertThat(expected).contains(actual);
//    }
//
//    @Test
//    public void getUserByEmailTest() {
//        Optional<User> expected = usersRepository.findByEmail(email);
//        User actual = usersService.getUser(email);
//
//        Assertions
//                .assertThat(expected).isEqualTo(actual);
//    }
//
////    @Test
////    public void setPasswordTest() {
////    }
//
////    @Test
////    public void updateUserTest() {
////    }
//
////    @Test
////    public void updateUserImageTest() {
////        imageRepository.save(image);
////        users.setImage(image);
////        usersRepository.save(users);
////
////        String expected = users.getImage().getId().;
////        ?????????????
////        imageRepository.deleteAll();
////    }
//
//    @Test
//    public void getUserByIdTest() {
//        Collection<User> expected = usersRepository.findAll();
//        User actual = usersService.getUserById(users.getId());
//
//        Assertions
//                .assertThat(expected).hasSize(1);
//        Assertions
//                .assertThat(expected).contains(actual);
//    }
//}
