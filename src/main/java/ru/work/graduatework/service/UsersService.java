package ru.work.graduatework.service;

import liquibase.repackaged.net.sf.jsqlparser.util.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.MySecurityConfig.UserDetailsServiceImpl;
import ru.work.graduatework.dto.Role;
import ru.work.graduatework.repository.UsersRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Collection;

import static ru.work.graduatework.MySecurityConfig.MySecuritySettings.getUserDetailsFromContext;
@RequiredArgsConstructor
@Service
public class UsersService {

    private final Logger logger = LoggerFactory.getLogger(UsersService.class);
    private final UsersRepository usersRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;


    /**
     * @return Collection activeUsers
     * @author Korolchuk Vladislav
     * <br> <b> Method get Users </b> </br>
     */

    public Collection<Users> getUsers() { //User
//        return usersRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(EntityNotFoundException::new);
        return usersRepository.findAll();
    }

    /**
     * @param id Input parameter
     *           <br> Is used entity Users {@link Users} </br>
     *           <br> Is used repository {@link UsersRepository#save(Object)} </br>
     *           <br> Uses the active Users collection which contains active users </br>
     * @return {@link Users}
     * @author Korolchuk Vladislav
     * <br> <b> Method get User </b> </br>
     */

    public Users getUserById(long id) {
        return usersRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    }

    public Users createUser(Users user) {
        if (usersRepository.existsByEmail(user.getEmail())) {
            throw new ValidationException(String.format("User \"%s\" already exist", user.getEmail()));
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


    public Users updateUser(Users user) {
        Users updateUser = getUserById(getUserDetailsFromContext().getId());
        updateUser.setFirstName(user.getFirstName());
        updateUser.setLastName(user.getLastName());
        updateUser.setPhone(user.getPhone());
        return usersRepository.save(updateUser);
    }


    public String updateUserImage(MultipartFile imageDto) {
        return null;
    }

    public void newPassword(String newPassword, String currentPassword) {
        UserDetails userDetails = getUserDetailsFromContext();
        if (!passwordEncoder.matches(currentPassword, userDetails.getPassword())) {
            throw new BadCredentialsException("Password not correct");
        }
        userDetailsService.updatePassword(userDetails, passwordEncoder.encode(newPassword));
    }
    public Users updateRole(long id, Role role) {
        Users user = getUserById(id);
        user.setRole(role);
        usersRepository.save(user);
        return user;
    }

}
