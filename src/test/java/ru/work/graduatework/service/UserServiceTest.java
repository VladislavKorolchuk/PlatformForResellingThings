package ru.work.graduatework.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import liquibase.repackaged.net.sf.jsqlparser.util.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.Entity.User;
import ru.work.graduatework.dto.Role;
import ru.work.graduatework.dto.UserDto;
import ru.work.graduatework.repository.UserRepository;
import ru.work.graduatework.security.UserDetailsServiceImpl;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean
    private ImageService imageService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Test
    void testCreateStartingUsers() throws UnsupportedEncodingException {
        Image image = new Image();
        image.setData("AAAAAAAA".getBytes("UTF-8"));
        image.setFileSize(3L);
        image.setId(123L);
        image.setMediaType("Media Type");

        User user = new User();
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setImage(image);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setPhone("4105551212");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setRegDate(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setRole(Role.USER);
        when(userRepository.existsByEmail((String) any())).thenReturn(true);
        when(userRepository.save((User) any())).thenReturn(user);
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");
        assertThrows(ValidationException.class, () -> userService.createStartingUsers());
        verify(userRepository).existsByEmail((String) any());
        verify(passwordEncoder).encode((CharSequence) any());
    }


    @Test
    void testGetUsers() throws UnsupportedEncodingException {
        Image image = new Image();
        image.setData("AAAAAAAA".getBytes("UTF-8"));
        image.setFileSize(3L);
        image.setId(123L);
        image.setMediaType("Media Type");

        User user = new User();
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setImage(image);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setPhone("4105551212");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setRegDate(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setRole(Role.USER);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
        assertSame(user, userService.getUsers("jane.doe@example.org"));
        verify(userRepository).findByEmail((String) any());
    }

    @Test
    void testGetUsers2() {
        when(userRepository.findByEmail((String) any()))
                .thenThrow(new BadCredentialsException("Current method is - getUsers"));
        assertThrows(BadCredentialsException.class, () -> userService.getUsers("jane.doe@example.org"));
        verify(userRepository).findByEmail((String) any());
    }

    @Test
    void testUpdateUser() throws Exception {
        Image image = new Image();
        image.setData("AAAAAAAA".getBytes("UTF-8"));
        image.setFileSize(3L);
        image.setId(123L);
        image.setMediaType("Media Type");

        User user = new User();
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setImage(image);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setPhone("4105551212");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setRegDate(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setRole(Role.USER);
        Optional<User> ofResult = Optional.of(user);

        Image image1 = new Image();
        image1.setData("AAAAAAAA".getBytes("UTF-8"));
        image1.setFileSize(3L);
        image1.setId(123L);
        image1.setMediaType("Media Type");

        User user1 = new User();
        user1.setCity("Oxford");
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setImage(image1);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setPhone("4105551212");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setRegDate(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        user1.setRole(Role.USER);
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
        assertSame(user1,
                userService.updateUser(
                        new UserDto(1, "Jane", "Doe", "jane.doe@example.org", "4105551212", "Oxford", "2020-03-01", "Image"),
                        "jane.doe@example.org"));
        verify(userRepository).save((User) any());
        verify(userRepository).findByEmail((String) any());
    }


    @Test
    void testUpdateUserImage() throws IOException {
        Image image = new Image();
        image.setData("AAAAAAAA".getBytes("UTF-8"));
        image.setFileSize(3L);
        image.setId(123L);
        image.setMediaType("Media Type");

        User user = new User();
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setImage(image);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setPhone("4105551212");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setRegDate(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setRole(Role.USER);
        Optional<User> ofResult = Optional.of(user);

        Image image1 = new Image();
        image1.setData("AAAAAAAA".getBytes("UTF-8"));
        image1.setFileSize(3L);
        image1.setId(123L);
        image1.setMediaType("Media Type");

        User user1 = new User();
        user1.setCity("Oxford");
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setImage(image1);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setPhone("4105551212");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setRegDate(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        user1.setRole(Role.USER);
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);

        Image image2 = new Image();
        image2.setData("AAAAAAAA".getBytes("UTF-8"));
        image2.setFileSize(3L);
        image2.setId(123L);
        image2.setMediaType("Media Type");
        when(imageService.uploadImage((MultipartFile) any())).thenReturn(image2);
        assertEquals("/users/image/123",
                userService.updateUserImage(
                        new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8"))),
                        "jane.doe@example.org"));
        verify(userRepository).save((User) any());
        verify(userRepository).findByEmail((String) any());
        verify(imageService).uploadImage((MultipartFile) any());
    }


    @Test
    void testGetUserById() throws UnsupportedEncodingException {
        Image image = new Image();
        image.setData("AAAAAAAA".getBytes("UTF-8"));
        image.setFileSize(3L);
        image.setId(123L);
        image.setMediaType("Media Type");

        User user = new User();
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setImage(image);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setPhone("4105551212");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setRegDate(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setRole(Role.USER);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(user, userService.getUserById(123L));
        verify(userRepository).findById((Long) any());
    }


    @Test
    void testGetUserById2() {
        when(userRepository.findById((Long) any())).thenThrow(new BadCredentialsException("Msg"));
        assertThrows(BadCredentialsException.class, () -> userService.getUserById(123L));
        verify(userRepository).findById((Long) any());
    }


    @Test
    void testCreateUser() throws UnsupportedEncodingException {
        Image image = new Image();
        image.setData("AAAAAAAA".getBytes("UTF-8"));
        image.setFileSize(3L);
        image.setId(123L);
        image.setMediaType("Media Type");

        User user = new User();
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setImage(image);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setPhone("4105551212");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setRegDate(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setRole(Role.USER);
        when(userRepository.existsByEmail((String) any())).thenReturn(true);
        when(userRepository.save((User) any())).thenReturn(user);

        Image image1 = new Image();
        image1.setData("AAAAAAAA".getBytes("UTF-8"));
        image1.setFileSize(3L);
        image1.setId(123L);
        image1.setMediaType("Media Type");

        User user1 = new User();
        user1.setCity("Oxford");
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setImage(image1);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setPhone("4105551212");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setRegDate(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        user1.setRole(Role.USER);
        assertThrows(ValidationException.class, () -> userService.createUser(user1));
        verify(userRepository).existsByEmail((String) any());
    }


    @Test
    void testUpdateRole() throws UnsupportedEncodingException {
        Image image = new Image();
        image.setData("AAAAAAAA".getBytes("UTF-8"));
        image.setFileSize(3L);
        image.setId(123L);
        image.setMediaType("Media Type");

        User user = new User();
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setImage(image);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setPhone("4105551212");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setRegDate(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setRole(Role.USER);
        Optional<User> ofResult = Optional.of(user);

        Image image1 = new Image();
        image1.setData("AAAAAAAA".getBytes("UTF-8"));
        image1.setFileSize(3L);
        image1.setId(123L);
        image1.setMediaType("Media Type");

        User user1 = new User();
        user1.setCity("Oxford");
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setImage(image1);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setPhone("4105551212");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setRegDate(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        user1.setRole(Role.USER);
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(user1, userService.updateRole(123L, Role.USER));
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
    }

}

