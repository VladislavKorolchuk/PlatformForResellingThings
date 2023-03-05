package ru.work.graduatework.dto;

import lombok.Data;

@Data
public class LoginReqDto {

    private String password;
    private String username;

    public LoginReqDto(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
