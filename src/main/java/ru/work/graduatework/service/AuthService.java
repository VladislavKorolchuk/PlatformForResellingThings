package ru.work.graduatework.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.dto.RegisterReqDto;
import ru.work.graduatework.dto.Role;
import ru.work.graduatework.mapper.UserMapper;
import ru.work.graduatework.repository.UserRepository;

@RequiredArgsConstructor
@Service()
public class AuthService {

  private final Logger logger = LoggerFactory.getLogger(AuthService.class);
  private final UserDetailsManager manager;
  private final UserRepository userRepository;
  private final PasswordEncoder encoder;
  private final UserMapper userMapper;
  private final UserService userService;

  public boolean login(String userName, String password) {
    logger.info("Current method is - login");
    if (!manager.userExists(userName)) {
      return false;
    }

    logger.info("Current method is - login");
    logger.info("login - " + userName);
    logger.info("password - " + password);
    logger.info("The user is found in the database");
    Users user = userRepository.findByEmail(userName).orElseThrow();

    if (encoder.matches(password, user.getPassword())) {
      return true;
    }
    return false;

  }

  public boolean register(RegisterReqDto registerReqDto, Role role) {
    logger.info("Current method is - register");
    Users user = userMapper.toEntity(registerReqDto);
    userService.createUser(user);
    manager.createUser(
        User.withDefaultPasswordEncoder()
            .password(registerReqDto.getPassword())
            .username(registerReqDto.getUsername())
            .roles(role.name())
            .build()
    );
    return true;
  }

}
