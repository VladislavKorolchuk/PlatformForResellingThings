package ru.work.graduatework.service;

import static ru.work.graduatework.dto.Role.ADMIN;
import static ru.work.graduatework.dto.Role.USER;
import static ru.work.graduatework.security.SecurityUtils.getUserDetailsFromContext;

import java.time.Instant;
import javax.annotation.PostConstruct;

import liquibase.repackaged.net.sf.jsqlparser.util.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.controller.UsersController;
import ru.work.graduatework.dto.CreateUserDto;
import ru.work.graduatework.dto.NewPasswordDto;
import ru.work.graduatework.dto.Role;
import ru.work.graduatework.dto.UserDto;
import ru.work.graduatework.repository.UserRepository;
import ru.work.graduatework.security.SecurityUtils;
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
     * <br> Is used entity Users {@link Users} </br>
     * <br> Is used entity Users {@link UserService#createUser(Users)} </br>
     *
     * @author Volkov Alexey
     */
    @PostConstruct
    public void createStartingUsers() {

        Users user = new Users();
        user.setCity("Test");
        user.setEmail("user@example.com");
        user.setRole(USER);
        user.setPhone("+79870000000");
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword(passwordEncoder.encode("password"));
        createUser(user);

    }

    /**
     * @param 'emailUser' Input parameter
     *                    <br> Is used entity Users {@link Users} </br>
     *                    <br> Is used repository {@link UserRepository#save(Object)} </br>
     * @return {@link Users}
     * Uses method {@link  UsersController#getUsers()}      UsersController#UsersController SeatsAvailability
     * @return {@link ru.work.graduatework.Entity.Users}
     * @author Korolchuk Vladislav
     */
    public Users getUsers() {

        return userRepository.findByEmail(SecurityUtils.getUserDetailsFromContext().getUsername()).orElseThrow();

    }

    /**
     * @param 'newPassword',currentPassword and email(name user) Input parameter
     *                                      <br> Is used entity Users {@link Users} </br>
     *                                      <br> Is used repository {@link UserRepository#save(Object)} </br>
     *                                      Uses method {@link  ru.work.graduatework.controller.UsersController#setPassword(NewPasswordDto)}  }
     * @author Korolchuk Vladislav
     */
    public void newPassword(String newPassword, String currentPassword) {

        UserDetails userDetails = getUserDetailsFromContext();
        if (!passwordEncoder.matches(currentPassword, userDetails.getPassword())) {
            throw new BadCredentialsException("The current password is incorrect!");
        }
        userDetailsService.updatePassword(userDetails, passwordEncoder.encode(newPassword));

    }

    /**
     * @param 'userDto',currentPassword and email(name user) Input parameter
     *                                  <br> Is used entity Users {@link Users} </br>
     *                                  <br> Is used entity Users {@link UserDto} </br>
     *                                  <br> Is used repository {@link UserRepository#save(Object)} </br>
     *                                  Uses method {@link  ru.work.graduatework.controller.UsersController#updateUser(UserDto)}  }
     * @return {@link ru.work.graduatework.Entity.Users}
     * @author Korolchuk Vladislav
     */
    public Users updateUser(UserDto userDto) {
        Users user = getUserById(getUserDetailsFromContext().getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        return userRepository.save(user);

    }

    /**
     * @param 'MultipartFile image' and email(name user) Input parameter
     *                       <br> Is used entity Users {@link Users} </br
     *                       <br> Is used repository {@link UserRepository#save(Object)} </br>
     *                       Uses method {@link  ru.work.graduatework.controller.UsersController#updateUserImage(MultipartFile)}  }
     * @return file path
     * @author Korolchuk Vladislav
     */
    @SneakyThrows
    public String updateUserImage(MultipartFile image) {
        Users user = getUserById(getUserDetailsFromContext().getId());
        user.setImage(imageService.uploadImage(image));
        return "/users/image/" + userRepository.save(user).getImage().getId();

    }

    /**
     * @param 'id User' Input parameter
     *            <br> Is used entity Users {@link Users} </br
     *            <br> Is used repository {@link UserRepository#save(Object)} </br>
     *            Uses method {@link  ru.work.graduatework.controller.UsersController#getUser(long)}
     * @return {@link ru.work.graduatework.Entity.Users}
     * @author Korolchuk Vladislav
     */
    public Users getUserById(long id) {

        return userRepository.findById(id).orElseThrow();

    }

    /**
     * @param 'User' Input parameter
     *               <br> Is used entity Users {@link Users} </br
     *               <br> Is used repository {@link UserRepository#save(Object)} </br>
     *               Uses method {@link  ru.work.graduatework.controller.UsersController#addUser(CreateUserDto)}
     * @return {@link ru.work.graduatework.Entity.Users}
     * @author Korolchuk Vladislav
     */
    public Users createUser(Users user) {

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
     *            <br> Is used entity Users {@link Users} </br
     *            <br> Is used repository {@link UserRepository#save(Object)} </br>
     *            Uses method {@link  ru.work.graduatework.controller.UsersController#updateRole(long, Role)}
     * @return User
     * @author Korolchuk Vladislav
     */
    public Users updateRole(long id, Role role) {

        Users user = getUserById(id);
        user.setRole(role);
        return userRepository.save(user);

    }

}
