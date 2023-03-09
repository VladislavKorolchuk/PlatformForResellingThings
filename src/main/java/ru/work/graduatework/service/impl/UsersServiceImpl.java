package ru.work.graduatework.service.impl;

import org.springframework.stereotype.Service;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.Entity.NewPassword;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.dto.UserDto;
import ru.work.graduatework.dto.repository.UsersRepository;
import ru.work.graduatework.mapper.UsersMapper;
import ru.work.graduatework.service.UsersService;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;


    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public Users getUsers() {
        return null;
    }

    @Override
    public NewPassword setPassword() {
        return null;
    }

    @Override
    public Users updateUser(UserDto userDto) {
        Users user;
        user=UsersMapper.toEntity(userDto);
        Optional<Users> updateUser= usersRepository.findByEmail(userDto.getEmail());
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
