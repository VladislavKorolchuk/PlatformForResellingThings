package ru.work.graduatework.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.work.graduatework.Entity.User;
import ru.work.graduatework.dto.RegisterReqDto;
import ru.work.graduatework.dto.Role;
import ru.work.graduatework.mapper.UserMapper;
import ru.work.graduatework.repository.UserRepository;

@RequiredArgsConstructor
@Service()
public class AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    private final UserService userService;

    public boolean login(String userName, String password) throws Exception {

        logger.info("Current method is - login");
        User user = userRepository.findByEmail(userName).orElseThrow(() -> new Exception(userName + " A user with this name is not registered"));
        logger.info("Current method is - login");
        return encoder.matches(password, user.getPassword());

    }

    public boolean register(RegisterReqDto registerReqDto, Role role) {

        logger.info("Current method is - register");
        User user = userMapper.toEntity(registerReqDto);
        userService.createUser(user);
        return true;

    }

}
