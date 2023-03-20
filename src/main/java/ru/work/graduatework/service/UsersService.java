package ru.work.graduatework.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.dto.NewPasswordDto;
import ru.work.graduatework.dto.UserDto;
import ru.work.graduatework.repository.UsersRepository;
import ru.work.graduatework.mapper.UsersMapper1;
import ru.work.graduatework.security.SecurityUtils;

import static ru.work.graduatework.security.SecurityUtils.getUserDetailsFromContext;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UsersService {

    private final Logger logger = LoggerFactory.getLogger(UsersService.class);
    private final UsersRepository usersRepository;
    private final ImageService imageService;

    public UsersService(UsersRepository usersRepository, ImageService imageService) {
        this.usersRepository = usersRepository;
        this.imageService = imageService;
    }

    Collection<Long> activeUsers = new HashSet<>(); // Collection active Users

    /**
     * @return Collection activeUsers
     * @author Korolchuk Vladislav
     * <br> <b> Method get Users </b> </br>
     */

    public Users getUsers() {

        return usersRepository.findByEmail(SecurityUtils.getUserDetailsFromContext().getUsername()).orElseThrow();
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


//    public UserDto updateUser(UserDto userDto) {
//        Users user;
//        user = UsersMapper1.toEntity(userDto);
//        Optional<Users> updateUser = usersRepository.findByEmail(userDto.getEmail());
//        if (updateUser.get() != null) {
//            usersRepository.save(updateUser.get());
//            return UsersMapper1.toDto(updateUser.get());
//        }
//        return null;
//    }


    public String updateUserImage(MultipartFile imageDto) {
        logger.info("Class UsersController, current method is - updateUserImage");
        Users users = new Users();  //
        usersRepository.save(users);
        Image image = new Image();
//        try {
//            imageService.addUserImage();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        users.setImage(image);
        return "/users/image" + usersRepository.save(users).getImage().getId();
    }

    public Users getUserById(long id) {
        return usersRepository.findById(id).orElseThrow();
    }



}
