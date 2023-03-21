package ru.work.graduatework.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.MySecurityConfig.UserDetailsServiceImpl;
import ru.work.graduatework.dto.RegisterReqDto;
import ru.work.graduatework.dto.Role;
import ru.work.graduatework.mapper.UsersMapper;
import ru.work.graduatework.repository.UsersRepository;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder encoder;
    private final UsersRepository usersRepository;
    private final UsersMapper userMapper;
    private final UsersService usersService;
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);



    public boolean login(String userName, String password) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            String encryptedPassword = userDetails.getPassword();
            return encoder.matches(password, encryptedPassword);
    }


    public boolean register(RegisterReqDto registerReqDto, Role role) {
        Users user = userMapper.toEntity(registerReqDto);
        if (usersRepository.existsByEmail(user.getEmail())) {
            throw new EntityNotFoundException();}
            user.setPassword(encoder.encode(user.getPassword()));
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


