package ru.work.graduatework.security;

import lombok.Getter;
import ru.work.graduatework.Entity.User;

import java.util.List;

@Getter
public class MyUserDetails extends org.springframework.security.core.userdetails.User{

    private final Long id;

    public MyUserDetails (User user) {
        super(user.getEmail(), user.getPassword(), List.of(user.getRole()));
        this.id=user.getId();
    }

    @Override
    public void eraseCredentials() {

    }

}
