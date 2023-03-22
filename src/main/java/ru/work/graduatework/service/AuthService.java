package ru.work.graduatework.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.dto.RegisterReqDto;
import ru.work.graduatework.dto.Role;
import ru.work.graduatework.mapper.UserMapper;
import ru.work.graduatework.repository.UserRepository;
import ru.work.graduatework.security.UserDetailsServiceImpl;

import javax.validation.ValidationException;
import java.time.Instant;

@RequiredArgsConstructor
@Service()
public class AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserDetailsServiceImpl userDetailsServiceImpl;


    public boolean login(String userName, String password) {
        try {
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userName);
            String encryptedPassword = userDetails.getPassword();
            return encoder.matches(password, encryptedPassword);
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException(String.format("User \"%s\" does not exist!", userName));
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException("Wrong password!");
        }
    }

    public boolean register(RegisterReqDto registerReqDto, Role role) {
        Users user = userMapper.toEntity(registerReqDto);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ValidationException(String.format("User \"%s\" is already registered!", user.getEmail()));
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRegDate(Instant.now());
        userRepository.save(user);
        return true;
    }

//    public boolean register(RegisterReqDto registerReqDto, Role role) {
//        logger.info("Current method is - register");
//        Users user = userMapper.toEntity(registerReqDto);
//        userService.createUser(user);
////        manager.createUser(
////                User.withDefaultPasswordEncoder()
////                        .password(registerReqDto.getPassword())
////                        .username(registerReqDto.getUsername())
////                        .roles(role.name())
////                        .build()
////        );
//        return true;
//    }

//    public boolean register(RegisterReqDto registerReqDto, Role role) {
//        Users user = userMapper.toEntity(registerReqDto);
//// if (usersRepository.existsByEmail(user.getEmail())) {
//// throw new EntityNotFoundException();}
//        user.setPassword(encoder.encode(user.getPassword()));
//        userService.createUser(user);
//        return true;
//    }

}
