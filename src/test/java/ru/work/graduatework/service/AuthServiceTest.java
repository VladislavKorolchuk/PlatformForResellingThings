package ru.work.graduatework.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.Entity.User;
import ru.work.graduatework.dto.RegisterReqDto;
import ru.work.graduatework.dto.Role;
import ru.work.graduatework.mapper.UserMapper;
import ru.work.graduatework.repository.UserRepository;

@ContextConfiguration(classes = {AuthService.class})
@ExtendWith(SpringExtension.class)
class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;


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
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
        when(passwordEncoder.matches((CharSequence) any(), (String) any())).thenReturn(true);
        assertTrue(authService.login("janedoe", "iloveyou"));
        verify(userRepository).findByEmail((String) any());
        verify(passwordEncoder).matches((CharSequence) any(), (String) any());
    }

    @Test
    void testLogin2() throws Exception {
        when(userRepository.findByEmail((String) any())).thenReturn(Optional.empty());
        when(passwordEncoder.matches((CharSequence) any(), (String) any())).thenReturn(true);
        assertThrows(Exception.class, () -> authService.login("janedoe", "iloveyou"));
        verify(userRepository).findByEmail((String) any());
    }

    @Test
    void testRegister() throws UnsupportedEncodingException {
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
        when(userMapper.toEntity((RegisterReqDto) any())).thenReturn(user);

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
        when(userService.createUser((User) any())).thenReturn(user1);

        RegisterReqDto registerReqDto = new RegisterReqDto();
        registerReqDto.setFirstName("Jane");
        registerReqDto.setLastName("Doe");
        registerReqDto.setPassword("iloveyou");
        registerReqDto.setPhone("4105551212");
        registerReqDto.setRole(Role.USER);
        registerReqDto.setUsername("janedoe");
        assertTrue(authService.register(registerReqDto, Role.USER));
        verify(userMapper).toEntity((RegisterReqDto) any());
        verify(userService).createUser((User) any());
    }
}

