package ru.work.graduatework.service;

import static ru.work.graduatework.dto.Role.USER;
import static ru.work.graduatework.security.SecurityUtils.getUserDetailsFromContext;

import java.time.Instant;
import javax.annotation.PostConstruct;

import liquibase.repackaged.net.sf.jsqlparser.util.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.User;
import ru.work.graduatework.dto.CreateUserDto;
import ru.work.graduatework.dto.NewPasswordDto;
import ru.work.graduatework.dto.Role;
import ru.work.graduatework.dto.UserDto;
import ru.work.graduatework.repository.UserRepository;
import ru.work.graduatework.security.UserDetailsServiceImpl;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Starting values User it starts when the system starts
     * <br> Is used entity User {@link User} </br>
     * <br> Is used entity User {@link UserService#createUser(User)} </br>
     *
     * @author Volkov Alexey
     */
    @PostConstruct
    public void createStartingUsers() {

        logger.info("Current method is - createStartingUsers");
        User user = new User();
        user.setCity("Test");
        user.setEmail("user@example.com");
        user.setRole(USER);
        user.setPhone("+79870000000");
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword("password");
        createUser(user);

    }

    /**
     * @param 'emailUser' Input parameter
     *                    <br> Is used entity User {@link User} </br>
     *                    <br> Is used repository {@link UserRepository#save(Object)} </br>
     * @return {@link User}
     * Uses method {@link  ru.work.graduatework.controller.UsersController#getUser(long)}      UsersController#UsersController SeatsAvailability
     * @return {@link User}
     * @author Korolchuk Vladislav
     */
    public User getUsers(String email) {

        logger.info("Current method is - getUsers");
        return userRepository.findByEmail(email).orElseThrow();

    }

    /**
     * @param 'newPassword',currentPassword and email(name user) Input parameter
     *                                      <br> Is used entity User {@link User} </br>
     *                                      <br> Is used repository {@link UserRepository#save(Object)} </br>
     *                                      Uses method {@link  ru.work.graduatework.controller.UsersController#setPassword(NewPasswordDto)}  }
     * @author Korolchuk Vladislav
     */
    public void newPassword(String newPassword, String currentPassword) {

        logger.info("Current method is - newPassword");
        UserDetails userDetails = getUserDetailsFromContext();
        if (!passwordEncoder.matches(currentPassword, userDetails.getPassword())) {
            throw new BadCredentialsException("The current password is incorrect!");
        }
        userDetailsService.updatePassword(userDetails, passwordEncoder.encode(newPassword));

    }

    /**
     * @param 'userDto',currentPassword and email(name user) Input parameter
     *                                  <br> Is used entity User {@link User} </br>
     *                                  <br> Is used entity User {@link UserDto} </br>
     *                                  <br> Is used repository {@link UserRepository#save(Object)} </br>
     *                                  Uses method {@link  ru.work.graduatework.controller.UsersController#updateUser(UserDto)}  }
     * @return {@link User}
     * @author Korolchuk Vladislav
     */
    public User updateUser(UserDto userDto, String email) throws Exception {

        logger.info("Current method is - updateUser");
        User user = userRepository.findByEmail(email).orElseThrow(()-> new Exception("User not found"));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        return userRepository.save(user);

    }

    /**
     * @param 'MultipartFile image' and email(name user) Input parameter
     *                       <br> Is used entity User {@link User} </br
     *                       <br> Is used repository {@link UserRepository#save(Object)} </br>
     *                       Uses method {@link  ru.work.graduatework.controller.UsersController#updateUserImage(MultipartFile)}  }
     * @return file path
     * @author Korolchuk Vladislav
     */
    @SneakyThrows
    public String updateUserImage(MultipartFile image, String email) {

        logger.info("Current method is - updateUserImage");
        User user = userRepository.findByEmail(email).orElseThrow();
        user.setImage(imageService.uploadImage(image));
        return "/users/image/" + userRepository.save(user).getImage().getId();

    }

    /**
     * @param 'id User' Input parameter
     *            <br> Is used entity User {@link User} </br
     *            <br> Is used repository {@link UserRepository#save(Object)} </br>
     *            Uses method {@link  ru.work.graduatework.controller.UsersController#getUser(long)}
     * @return {@link User}
     * @author Korolchuk Vladislav
     */
    public User getUserById(long id) {

        return userRepository.findById(id).orElseThrow();

    }

    /**
     * @param 'User' Input parameter
     *               <br> Is used entity User {@link User} </br
     *               <br> Is used repository {@link UserRepository#save(Object)} </br>
     *               Uses method {@link  ru.work.graduatework.controller.UsersController#addUser(CreateUserDto)}
     * @return {@link User}
     * @author Korolchuk Vladislav
     */
    public User createUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ValidationException(String.format("User \"%s\" already exists", user.getEmail()));
        }
        if (user.getRole() == null) {
            user.setRole(USER);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegDate(Instant.now());
        return userRepository.save(user);

    }

    /**
     * @param 'id User and role' Input parameter
     *            <br> Is used entity User {@link User} </br
     *            <br> Is used repository {@link UserRepository#save(Object)} </br>
     *            Uses method {@link  ru.work.graduatework.controller.UsersController#updateRole(long, Role)}
     * @return User
     * @author Korolchuk Vladislav
     */
    public User updateRole(long id, Role role) {

        User user = getUserById(id);
        user.setRole(role);
        return userRepository.save(user);

    }

}
