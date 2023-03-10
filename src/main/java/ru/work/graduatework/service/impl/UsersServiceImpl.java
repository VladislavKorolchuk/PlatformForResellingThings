package ru.work.graduatework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.Entity.NewPassword;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.controller.UsersController;
import ru.work.graduatework.dto.UserDto;
import ru.work.graduatework.dto.repository.UsersRepository;
import ru.work.graduatework.mapper.UsersMapper;
import ru.work.graduatework.service.UsersService;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);
    private final UsersRepository usersRepository;


    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Users getUsers(String emailUser) {

        logger.info("Class UsersServiceImpl, current method is - getUsers");
        Optional<Users> userFindByEmail = usersRepository.findByEmail(emailUser);
        logger.info("ID Logged in user - " + userFindByEmail.get().getId().toString());
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
