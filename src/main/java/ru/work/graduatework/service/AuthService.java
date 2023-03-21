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
import ru.work.graduatework.repository.UsersRepository;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service()
public class AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserDetailsManager manager;
    private final UsersRepository usersRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    private final UsersService usersService;

    public boolean login(String userName, String password) {
        logger.info("Current method is - login");
        if (!manager.userExists(userName)) {
            return false;
        }
        logger.info("Current method is - login");
        logger.info("login - " + userName);
        logger.info("password - " + password);
        if (usersRepository.findByEmail(userName).isPresent() == true) {
            logger.info("The user is found in the database");
            Users user = usersRepository.findByEmail(userName).orElseThrow();
            if (user.getCurrentPassword().equals(password)) {
                return true;
            }

//            if (encoder.matches(password, user.getCurrentPassword()) == true) {
//                return true;
//            }
        }
        return false;

    }

    public boolean register(RegisterReqDto registerReqDto, Role role) {
        logger.info("Current method is - register");
        Users user = userMapper.toEntity(registerReqDto);
        usersService.createUser(user);
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
