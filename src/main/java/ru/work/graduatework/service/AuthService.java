package ru.work.graduatework.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
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

    /**
     * @param userName name identification
     * @param password password identification
     *                 <br> Is used entity User {@link User} </br>
     *                 <br> Is used repository {@link UserRepository#save(Object)} </br>
     * @return boolean true - user identified, false = user not identified
     * @throws Exception
     */
    public boolean login(String userName, String password) throws Exception {

        logger.info("Current method is - login");
        User user = userRepository.findByEmail(userName).orElseThrow(() ->
                new Exception("A user with this name is not registered"));
        logger.info("Current method is - login");

        return encoder.matches(password, user.getPassword());
    }

    /**
     * @param registerReqDto entity {@link RegisterReqDto}
     * @param role           role user USER/ADMIN
     *                       <br> Is used entity User {@link User} </br>
     *                       <br> Is used repository {@link UserService#createUser(User)} </br>
     * @return boolean true - user created , false = not created
     */

    public boolean register(RegisterReqDto registerReqDto, Role role) {

        logger.info("Current method is - register");
        User user = userMapper.toEntity(registerReqDto);
        userService.createUser(user);
        return true;

    }

}
