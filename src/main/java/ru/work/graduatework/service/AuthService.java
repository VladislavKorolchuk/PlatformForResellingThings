package ru.work.graduatework.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.work.graduatework.dto.RegisterReqDto;
import ru.work.graduatework.dto.Role;
import ru.work.graduatework.repository.UsersRepository;

@Service()
public class AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserDetailsManager manager;
    private final UsersRepository usersRepository;
    private final PasswordEncoder encoder;

    private final UsersService usersService;

    public AuthService(UserDetailsManager manager, UsersRepository usersRepository, UsersService usersService) {
        this.usersRepository = usersRepository;
        this.manager = manager;
        this.usersService = usersService;
        this.encoder = new BCryptPasswordEncoder();
    }


    public boolean login(String userName, String password) {
        logger.info("Current method is - login");
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        String encryptedPassword = userDetails.getPassword();
        String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(8);
        return encoder.matches(password, encryptedPasswordWithoutEncryptionType);

    }


    public boolean register(RegisterReqDto registerReqDto, Role role) {
        logger.info("Current method is - register");
//        createUser(registerReqDto);   // method of adding a new user
        if (manager.userExists(registerReqDto.getUsername())) {
            return false;
        }
        manager.createUser(
                User.withDefaultPasswordEncoder()
                        .password(registerReqDto.getPassword())
                        .username(registerReqDto.getUsername())
                        .roles(role.name())
                        .build()
        );
        return true;
    }

    /**
     * @param registerReqDto Input parameter
     *                       <br> <b> Method create User </b> </br>
     *                       <br> Input parameter entity {@link RegisterReqDto} </br>
     *                       <br> Is used entity Users {@link User} </br>
     *                       <br> Is used repository {@link UsersRepository#save(Object)} </br>
     *                       * @return boolean true - New user added or false - New user not added
     * @author Korolchuk Vladislav
     */




}
