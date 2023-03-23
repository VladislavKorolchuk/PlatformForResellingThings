package ru.work.graduatework.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
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
    private final UserDetailsManager manager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    private final UserService userService;

    /**
     * @param 'String userName, String password' - login and password
     *                <br> Is used entity User {@link User} </br>
     *                <br> Is used repository {@link UserRepository#save(Object)} </br>
     *                <br>Uses method {@link  ru.work.graduatework.service.UserService#getUsers(String)} (NewPasswordDto)}  } </br>
     *                <br> Uses mapper {@link  ru.work.graduatework.mapper.UserMapper#toEntity(RegisterReqDto)} (NewPasswordDto)}  } </br>
     * @return boolean true- authorization successful/false - authorization failed
     */
    public boolean login(String userName, String password) {
        logger.info("Current method is - login");
        if (!manager.userExists(userName)) {
            return false;
        }
        logger.info("login - " + userName);
        logger.info("password - " + password);
        User user = userRepository.findByEmail(userName).orElseThrow();
        if (encoder.matches(password, user.getPassword())) {
            logger.info("The user has been authorized");
            return true;
        }
        logger.info("The user did not pass authorization");
        return false;
    }

    /**
     * @param registerReqDto - contains registration data
     *                       <br> Is used entity User {@link User} </br>
     *                       Uses method {@link  ru.work.graduatework.service.UserService#createUser(User)} (NewPasswordDto)}  }
     *                       Uses mapper {@link  ru.work.graduatework.mapper.UserMapper#toEntity(RegisterReqDto)} (NewPasswordDto)}  }
     * @param role
     * @return boolean true - successful addition user/false - failed addition
     */
    public boolean register(RegisterReqDto registerReqDto, Role role) {
        logger.info("Current method is - register");
        User user = userMapper.toEntity(registerReqDto);
        if (userService.createUser(user) == null) {
            return false;
        }
        return true;
    }

}
