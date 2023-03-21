package ru.work.graduatework.service;

import static ru.work.graduatework.dto.Role.USER;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import javax.annotation.PostConstruct;
import liquibase.repackaged.net.sf.jsqlparser.util.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.dto.NewPasswordDto;
import ru.work.graduatework.dto.Role;
import ru.work.graduatework.dto.UserDto;
import ru.work.graduatework.repository.UsersRepository;

@Transactional
@RequiredArgsConstructor
@Service
public class UsersService {

  private final Logger logger = LoggerFactory.getLogger(UsersService.class);
  private final UsersRepository usersRepository;
  private final ImageService imageService;

  private final PasswordEncoder passwordEncoder;

  Collection<Long> activeUsers = new HashSet<>(); // Collection active Users

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
   * @return Collection activeUsers
   * @author Korolchuk Vladislav
   * <br> <b> Method get Users </b> </br>
   */

// Uses method - Users    controller - UsersController
  public Users getUsers(String email) {
    return usersRepository.findByEmail(email).orElseThrow();
  }

  public String dateUserRegistration() {
    return String.valueOf(LocalDate.now());
  }

  /**
   * @param emailUser Input parameter
   *                  <br> Is used entity Users {@link Users} </br>
   *                  <br> Is used repository {@link UsersRepository#save(Object)} </br>
   *                  <br> Uses the active Users collection which contains active users </br>
   * @return {@link Users}
   * @author Korolchuk Vladislav
   * <br> <b> Method get User </b> </br>
   */

  public Users getUser(String emailUser) {

    logger.info("Class UsersServiceImpl, current method is - getUser");

    Optional<Users> userFindByEmail = usersRepository.findByEmail(emailUser);
    //---- Creating a User entity that has been authenticated by the system----
    if (userFindByEmail.isPresent()) {
      activeUsers.add(userFindByEmail.get().getId());
    }
    return userFindByEmail.orElse(null);

  }


  public NewPasswordDto setPassword() {
    return null;
  }


  // Uses method - setPassword    controller - UsersController
  public void newPassword(String newPassword, String currentPassword, String email) {

    Users user = usersRepository.findByEmail(email).orElseThrow();
    if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
      throw new BadCredentialsException("The password is incorrect");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));

  }


  // Uses method - updateUser    controller - UsersController
  public Users updateUser(UserDto userDto, String email) {

    Users user = usersRepository.findByEmail(email).orElseThrow();
    user.setFirstName(userDto.getFirstName());
    user.setLastName(userDto.getLastName());
    user.setPhone(userDto.getPhone());
    return usersRepository.save(user);


  }

  // Uses method - updateUserImage    controller - UsersController
  @SneakyThrows
  public String updateUserImage(MultipartFile image, String email) {

    logger.info("Current method is - updateUserImage");
    Users user = usersRepository.findByEmail(email).orElseThrow();
    user.setImage(imageService.uploadImage(image));
    return "/users/image/" + usersRepository.save(user).getImage().getId();

  }


  // Uses method - User    controller - UsersController
  public Users getUserById(long id) {
    return usersRepository.findById(id).orElseThrow();
  }

  // Uses method - addUser    controller - UsersController
  public Users createUser(Users user) {
    if (usersRepository.existsByEmail(user.getEmail())) {
      throw new ValidationException(String.format("User \"%s\" already exists", user.getEmail()));
    }

    if (user.getRole() == null) {
      user.setRole(USER);
    }
    // user.setCurrentPassword(passwordEncoder.encode(user.getCurrentPassword()));
    user.setRegDate(Instant.now());
    return usersRepository.save(user);
  }


  // Uses method - updateRole    controller - UsersController
  public Users updateRole(long id, Role role) {
    Users user = getUserById(id);
    user.setRole(role);
    return usersRepository.save(user);
  }


}
