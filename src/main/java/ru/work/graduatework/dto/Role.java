package ru.work.graduatework.dto;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
