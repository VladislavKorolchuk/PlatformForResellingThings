package ru.work.graduatework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.Entity.NewPassword;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.dto.UserDto;
import ru.work.graduatework.dto.repository.UsersRepository;
import ru.work.graduatework.mapper.UsersMapper;
import ru.work.graduatework.service.UsersService;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);
    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    Collection <Integer> activeUsers = new HashSet<>(); // Collection active Users

    /**
     * @author Korolchuk Vladislav
     * <br> <b> Method get Users </b> </br>
     * @param emailUser Input parameter
     * <br> Is used entity Users {@link Users} </br>
     * <br> Is used repository {@link UsersRepository#save(Object)} </br>
     * <br> Uses the active Users collection which contains active users </br>
     * @return {@link Users}
     */
    @Override
    public Users getUsers(String emailUser) {

        logger.info("Class UsersServiceImpl, current method is - getUsers");
        Optional<Users> userFindByEmail = usersRepository.findByEmail(emailUser);
        // Filling the collection with active users
        if (userFindByEmail.isPresent()) {
            activeUsers.add(userFindByEmail.get().getId());
            logger.info("ID Logged in user - " + userFindByEmail.get().getId().toString());
        }

        return userFindByEmail.orElse(null);

    }

    @Override
    public NewPassword setPassword() {
        return null;
    }

    @Override
    public Users updateUser(UserDto userDto) {
        Users user;
        user = UsersMapper.toEntity(userDto);
        Optional<Users> updateUser = usersRepository.findByEmail(userDto.getEmail());
        if (updateUser.get() != null) {
            usersRepository.save(updateUser.get());
            return updateUser.get();
        }
        return null;
    }

    @Override
    public Image updateUserImage() {
        return null;
    }
}
