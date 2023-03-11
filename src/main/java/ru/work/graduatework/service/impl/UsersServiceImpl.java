package ru.work.graduatework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.work.graduatework.Old_Entity.Image;
import ru.work.graduatework.Old_Entity.NewPassword;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.dto.UserDto;
import ru.work.graduatework.dto.repository.UsersRepository;
import ru.work.mapper.UsersMapper;
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

    Collection<Integer> activeUsers = new HashSet<>(); // Collection active Users

    /**
     * @return Collection activeUsers
     * @author Korolchuk Vladislav
     * <br> <b> Method get Users </b> </br>
     */
    @Override
    public Collection<Users> getUsers() {

        Collection<Users> usersCollection = new HashSet();
        for (Integer idUser : activeUsers) {
            Optional<Users> userFindById = usersRepository.findById(idUser);
            if (userFindById.isPresent()) {
                Users user = new Users();
                user.setId(userFindById.get().getId());
                user.setFirstName(userFindById.get().getFirstName());
                user.setLastName(userFindById.get().getLastName());
                user.setEmail(userFindById.get().getEmail());
                user.setPhone(userFindById.get().getPhone());
                usersCollection.add(user);
            }
        }
        return usersCollection;

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
    @Override
    public Users getUser(String emailUser) {

        logger.info("Class UsersServiceImpl, current method is - getUser");

        Optional<Users> userFindByEmail = usersRepository.findByEmail(emailUser);
        //---- Creating a User entity that has been authenticated by the system----
        if (userFindByEmail.isPresent()) {
            activeUsers.add(userFindByEmail.get().getId());
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
