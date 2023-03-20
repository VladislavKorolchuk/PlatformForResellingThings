package ru.work.graduatework.service;

import liquibase.repackaged.net.sf.jsqlparser.util.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.dto.Role;
import ru.work.graduatework.dto.UserDto;
import ru.work.graduatework.repository.UsersRepository;


import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

@Service
public class UsersService {

    private final Logger logger = LoggerFactory.getLogger(UsersService.class);
    private final UsersRepository usersRepository;
    private final ImageService imageService;

    private final PasswordEncoder passwordEncoder;
    public UsersService(UsersRepository usersRepository, ImageService imageService, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.imageService = imageService;
        this.passwordEncoder = passwordEncoder;
    }
    /**
     * @return Collection activeUsers
     * @author Korolchuk Vladislav
     * <br> <b> Method get Users </b> </br>
     */

    public Users getUsers() {
        return usersRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(EntityNotFoundException::new);
    }

    /**
     * @param id Input parameter
     *                  <br> Is used entity Users {@link Users} </br>
     *                  <br> Is used repository {@link UsersRepository#save(Object)} </br>
     *                  <br> Uses the active Users collection which contains active users </br>
     * @return {@link Users}
     * @author Korolchuk Vladislav
     * <br> <b> Method get User </b> </br>
     */

    public Users getUserById(long id) {
        return usersRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    }

    public Users createUser(Users user) {
        if (usersRepository.existsByEmail(user.getEmail())) {
            throw new ValidationException(String.format("Пользователь \"%s\" уже существует!", user.getEmail()));
        }
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegDate(dateUserRegistration());
        return usersRepository.save(user);
    }

    public String dateUserRegistration() {
        return String.valueOf(LocalDate.now());
    }
    public boolean setPassword(String newPassword, String currentPassword) {
        Users user = usersRepository.findByEmail(SecurityContextHolder.getContext()
                .getAuthentication().getName()).orElseThrow();


        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            usersRepository.save(user);
            return true;
        }
        return false;
    }


    public UserDto updateUser(UserDto userDto) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        usersRepository.setFirstName(userDto.getFirstName());
//        usersRepository.setLastName(userDto.getLastName());
//        usersRepository.setPhone(userDto.getPhone());
//
//        return userRepository.save(user);
        return null;
    }


    public String updateUserImage(MultipartFile imageDto) {
        return null;
    }

}
