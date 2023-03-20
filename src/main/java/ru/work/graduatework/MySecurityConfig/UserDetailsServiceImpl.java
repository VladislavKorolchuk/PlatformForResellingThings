package ru.work.graduatework.MySecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.repository.UsersRepository;
import ru.work.graduatework.service.UsersService;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserDetailsPasswordService {
    private final UsersRepository usersRepository;

    @Autowired
    public UserDetailsServiceImpl(UsersRepository userRepository, UsersService usersService) {
        this.usersRepository = userRepository;

    }

    private Users getUserByUsername(String username) {
        return usersRepository.findByEmail(username).orElseThrow(EntityNotFoundException::new);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = getUserByUsername(username);
        return new MyUserUtils(users);
    }


    @Override
    public UserDetails updatePassword(UserDetails userDetails, String newPassword) {
        Users user = getUserByUsername(userDetails.getUsername());
        user.setPassword(newPassword);
        MyUserUtils updatedUserDetails = new MyUserUtils(usersRepository.save(user));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(updatedUserDetails, null, updatedUserDetails.getAuthorities())
        );
        return updatedUserDetails;
    }
}
