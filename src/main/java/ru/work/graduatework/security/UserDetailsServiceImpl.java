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
import ru.work.graduatework.Entity.User;
import ru.work.graduatework.repository.UserRepository;

import javax.transaction.Transactional;

@Transactional
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Service
public class UserDetailsServiceImpl implements UserDetailsService,
        UserDetailsPasswordService {

    private final UserRepository usersRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        return new MyUserDetails(user);
    }

    private User getUserByUsername(String username) {
        return usersRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found is email"));
    }

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String newPassword) {
        User user = getUserByUsername(userDetails.getUsername());
        user.setPassword(newPassword);
        MyUserDetails updateUserDetails = new MyUserDetails(usersRepository.save(user));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(updateUserDetails,
                        null, updateUserDetails.getAuthorities())
        );
        return updateUserDetails;
    }


}
