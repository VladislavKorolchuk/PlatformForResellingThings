package ru.work.graduatework.MySecurityConfig;

import org.springframework.security.core.GrantedAuthority;
import ru.work.graduatework.Entity.Users;

import java.util.Collection;
import java.util.List;

public class MyUserUtils extends org.springframework.security.core.userdetails.User {
    private final Long id;

    public MyUserUtils(Users user) {
        super(user.getEmail(), user.getPassword(), List.of(user.getRole()));
        this.id = user.getId();
    }
    public Long getId() {
        return id;
    }
}
