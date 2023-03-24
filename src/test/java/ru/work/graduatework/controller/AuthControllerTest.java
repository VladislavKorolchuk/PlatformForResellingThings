package ru.work.graduatework.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.Entity.User;
import ru.work.graduatework.dto.LoginReqDto;
import ru.work.graduatework.dto.RegisterReqDto;
import ru.work.graduatework.dto.Role;
import ru.work.graduatework.repository.UserRepository;
import ru.work.graduatework.service.AuthService;

@ContextConfiguration(classes = {AuthController.class})
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    @Autowired
    private AuthController authController;

    @MockBean
    private AuthService authService;

    @Test
    void testLogin() throws Exception {

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
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByEmail((String) any())).thenReturn(Optional.of(user));
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();

        ResponseEntity<?> actualLoginResult = authController.login(new LoginReqDto("janedoe", "iloveyou"));
        assertNull(actualLoginResult.getBody());
        assertEquals(HttpStatus.FORBIDDEN, actualLoginResult.getStatusCode());
        assertTrue(actualLoginResult.getHeaders().isEmpty());

    }

    @Test
    void testRegister() throws Exception {
        when(authService.register((RegisterReqDto) any(), (Role) any())).thenReturn(true);

        RegisterReqDto registerReqDto = new RegisterReqDto();
        registerReqDto.setFirstName("Jane");
        registerReqDto.setLastName("Doe");
        registerReqDto.setPassword("iloveyou");
        registerReqDto.setPhone("4105551212");
        registerReqDto.setRole(Role.USER);
        registerReqDto.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(registerReqDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

