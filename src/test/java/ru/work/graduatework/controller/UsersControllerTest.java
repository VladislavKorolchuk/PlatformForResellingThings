package ru.work.graduatework.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.Entity.User;
import ru.work.graduatework.dto.CreateUserDto;
import ru.work.graduatework.dto.NewPasswordDto;
import ru.work.graduatework.dto.Role;
import ru.work.graduatework.dto.UserDto;
import ru.work.graduatework.mapper.UserMapper;
import ru.work.graduatework.mapper.UserMapperImpl;
import ru.work.graduatework.repository.ImageRepository;
import ru.work.graduatework.repository.UserRepository;
import ru.work.graduatework.security.UserDetailsServiceImpl;
import ru.work.graduatework.service.ImageService;
import ru.work.graduatework.service.UserService;

@ContextConfiguration(classes = {UsersController.class})
@ExtendWith(SpringExtension.class)
class UsersControllerTest {
    @MockBean
    private ImageService imageService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserService userService;

    @Autowired
    private UsersController usersController;


    @Test
    @Disabled("TODO: Complete this test")
    void testSetPassword() {
        UserRepository userRepository = mock(UserRepository.class);
        ImageService imageService = new ImageService(mock(ImageRepository.class));
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserService userService = new UserService(userRepository, imageService, passwordEncoder,
                new UserDetailsServiceImpl(mock(UserRepository.class)));

        ImageService imageService1 = new ImageService(mock(ImageRepository.class));
        UsersController usersController = new UsersController(userService, imageService1, new UserMapperImpl());
        usersController.setPassword(new NewPasswordDto("iloveyou", "iloveyou"));
    }

    @Test
    void testSetPassword2() {
        UserService userService = mock(UserService.class);
        doNothing().when(userService).newPassword((String) any(), (String) any());
        ImageService imageService = new ImageService(mock(ImageRepository.class));
        UsersController usersController = new UsersController(userService, imageService, new UserMapperImpl());
        ResponseEntity<NewPasswordDto> actualSetPasswordResult = usersController
                .setPassword(new NewPasswordDto("iloveyou", "iloveyou"));
        assertTrue(actualSetPasswordResult.hasBody());
        assertTrue(actualSetPasswordResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualSetPasswordResult.getStatusCode());
        verify(userService).newPassword((String) any(), (String) any());
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
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        ImageService imageService = new ImageService(mock(ImageRepository.class));
        Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
        UserService userService = new UserService(userRepository, imageService, passwordEncoder,
                new UserDetailsServiceImpl(mock(UserRepository.class)));

        ImageService imageService1 = new ImageService(mock(ImageRepository.class));
        ResponseEntity<UserDto> actualUpdateRoleResult = (new UsersController(userService, imageService1,
                new UserMapperImpl())).updateRole(123L, Role.USER);
        assertTrue(actualUpdateRoleResult.hasBody());
        assertTrue(actualUpdateRoleResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualUpdateRoleResult.getStatusCode());
        UserDto body = actualUpdateRoleResult.getBody();
        assertEquals("/user/image/123", body.getImage());
        assertEquals(123, body.getId());
        assertEquals("Jane", body.getFirstName());
        assertEquals("jane.doe@example.org", body.getEmail());
        assertEquals("Oxford", body.getCity());
        assertEquals("1970-01-01T00:00:00Z", body.getRegDate());
        assertEquals("Doe", body.getLastName());
        assertEquals("4105551212", body.getPhone());
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Long) any());
    }

    @Test
    void testGetImageById() throws Exception {
        Image image = new Image();
        image.setData("AAAAAAAA".getBytes("UTF-8"));
        image.setFileSize(3L);
        image.setId(123L);
        image.setMediaType("Media Type");
        when(imageService.getImageById(anyLong())).thenReturn(image);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/image/{id}", 123L);
        MockMvcBuilders.standaloneSetup(usersController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("image/png"))
                .andExpect(MockMvcResultMatchers.content().string("AAAAAAAA"));
    }


    @Test
    void testGetImageByIdNotFound() throws Exception {
        Image image = new Image();
        image.setData("AAAAAAAA".getBytes("UTF-8"));
        image.setFileSize(3L);
        image.setId(123L);
        image.setMediaType("Media Type");
        when(imageService.getImageById(anyLong())).thenReturn(image);
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders
                .formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(usersController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testGetUser() throws Exception {
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
        when(userService.getUserById(anyLong())).thenReturn(user);
        when(userMapper.toDto((User) any())).thenReturn(
                new UserDto(1, "Jane", "Doe", "jane.doe@example.org", "4105551212", "Oxford", "2020-03-01", "Image"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{id}", 123L);
        MockMvcBuilders.standaloneSetup(usersController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"jane.doe@example.org\",\"phone\":\"4105551212\",\"city"
                                        + "\":\"Oxford\",\"regDate\":\"2020-03-01\",\"image\":\"Image\"}"));
    }


    @Test
    void testGetUsers() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders
                .formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(usersController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testUpdateUserImage() throws Exception {
        MockHttpServletRequestBuilder patchResult = MockMvcRequestBuilders.patch("/users/me/image");
        MockHttpServletRequestBuilder requestBuilder = patchResult.param("image", String.valueOf((Object) null));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(usersController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }
}

