package ru.work.graduatework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.repository.UsersRepository;

import javax.transaction.Transactional;

@Transactional
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Service
public class UserDetailsServiceImpl implements UserDetailsService,
        UserDetailsPasswordService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserDetailsServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Users user = getUserByUsername(username);
        return new MyUserDetails(user);
    }

    private Users getUserByUsername(String username) {
        return usersRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found is email"));
    }

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String newPassword) {
        Users user = getUserByUsername(userDetails.getUsername());
        user.setCurrentPassword(newPassword);
        MyUserDetails updateUserDetails = new MyUserDetails(usersRepository.save(user));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(updateUserDetails,
                        null, updateUserDetails.getAuthorities())
        );
        return updateUserDetails;
    }


}
