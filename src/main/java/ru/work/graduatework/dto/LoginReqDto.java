package ru.work.graduatework.dto;

import lombok.Data;

@Data
public class LoginReqDto {
    private String password;
    private String username;

    public LoginReqDto(String username, String password) {
        this.password = password;
        this.username = username;

    }

}
