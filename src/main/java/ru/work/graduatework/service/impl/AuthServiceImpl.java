package ru.work.graduatework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.work.graduatework.Entity.RegisterReq;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.dto.RegisterReqDto;
import ru.work.graduatework.dto.Role;
import ru.work.graduatework.dto.repository.UsersRepository;
import ru.work.graduatework.mapper.RegisterReqMapper;
import ru.work.graduatework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserDetailsManager manager;

    private final UsersRepository usersRepository;

    private final PasswordEncoder encoder;

    public AuthServiceImpl(UserDetailsManager manager, UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.manager = manager;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean login(String userName, String password) {
        logger.info("Class AuthServiceImpl, current method is - login");
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        String encryptedPassword = userDetails.getPassword();
        String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(8);
        return encoder.matches(password, encryptedPasswordWithoutEncryptionType);
    }

    @Override
    public boolean register(RegisterReqDto registerReqDto, Role role) {
        logger.info("Class AuthServiceImpl, current method is - register");
        logger.info(registerReqDto.getUsername());
        logger.info(registerReqDto.getPassword());
        logger.info(registerReqDto.getPhone());
          createUser(registerReqDto);
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
        //    createUser(registerReqDto);
        return true;
    }

    public void createUser(RegisterReqDto registerReqDto) {
        logger.info("Class AuthServiceImpl, current method is - createUser");
        logger.info(registerReqDto.getUsername());
        logger.info(registerReqDto.getFirstName());
        logger.info(registerReqDto.getLastName());
        Users user = new Users();
        RegisterReq registerReq;
        registerReq = RegisterReqMapper.toEntity(registerReqDto);
        user.setEmail(registerReq.getUsername());
        user.setFirstName(registerReq.getFirstName());
        user.setLastName(registerReq.getLastName());
        user.setPhone(registerReq.getPhone());
        usersRepository.save(user);
    }

}
