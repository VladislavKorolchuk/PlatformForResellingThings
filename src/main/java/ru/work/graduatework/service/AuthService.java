package ru.work.graduatework.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.MySecurityConfig.UserDetailsServiceImpl;
import ru.work.graduatework.dto.RegisterReqDto;
import ru.work.graduatework.dto.Role;
import ru.work.graduatework.mapper.UserMapper;
import ru.work.graduatework.repository.UsersRepository;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder encoder;
    private final UsersRepository usersRepository;
    private final UserMapper userMapper;
    private final UsersService usersService;
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);



    public boolean login(String userName, String password) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            String encryptedPassword = userDetails.getPassword();
            return encoder.matches(password, encryptedPassword);
    }


    public boolean register(RegisterReqDto registerReqDto, Role role) {
        Users user = userMapper.toEntity(registerReqDto);
//        if (usersRepository.existsByEmail(user.getEmail())) {
//            throw new EntityNotFoundException();}
            user.setPassword(encoder.encode(registerReqDto.getPassword()));
            user.setRegDate(usersService.dateUserRegistration());
            usersRepository.save(user);
            return true;
    }
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


