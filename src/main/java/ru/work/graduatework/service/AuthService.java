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

    // --------------Ask Alexey why not put final  ------------------

    private UserMapper userMapper;

    // ---------------------------------------------------
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
            if (encoder.matches(password, user.getCurrentPassword()) == true) {
                return true;
            }
        }
        return false;

    }


    public boolean register(RegisterReqDto registerReqDto, Role role) {
        logger.info("Current method is - register");

       //  Users user = userMapper.toEntity(registerReqDto);
      //  if (usersRepository.existsByEmail(user.getEmail())) {
    //        throw new ValidationException(String.format("Пользователь \"%s\" уже зарегистрирован!", user.getEmail()));
     //   }


          createUser(registerReqDto);   // method of adding a new user
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
     *                       <br> Is used mapper {@link RegisterReqMapper} </br>
     *                       <br> Is used repository {@link UsersRepository#save(Object)} </br>
     *                       * @return boolean true - New user added or false - New user not added
     * @author Korolchuk Vladislav
     */

    public boolean createUser(RegisterReqDto registerReqDto) {

        logger.info("Current method is - createUser");
        if (registerReqDto != null) {
            if (registerReqDto.getUsername() != null & !registerReqDto.getUsername().isEmpty()) {
                Optional<Users> userFindByEmail;
                userFindByEmail = usersRepository.findByEmail(registerReqDto.getUsername());
                if (!userFindByEmail.isPresent()) {
                    Users user = new Users();
                    user.setEmail(registerReqDto.getUsername());
                    user.setFirstName(registerReqDto.getFirstName());
                    user.setLastName(registerReqDto.getLastName());
                    user.setPhone(registerReqDto.getPhone());
                    user.setCurrentPassword(encoder.encode(registerReqDto.getPassword()));
               //     user.setRegDate(dateUserRegistration());
                    user.setRole(Role.USER);
                    usersRepository.save(user);
                    return true;  // New user added
                }
            }
        }
        return false;             // New user not added

    }

    public String dateUserRegistration() {
        return String.valueOf(LocalDate.now());
    }
}
