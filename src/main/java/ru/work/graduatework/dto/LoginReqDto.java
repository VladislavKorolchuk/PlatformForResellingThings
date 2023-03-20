package ru.work.graduatework.dto;

import lombok.Data;

@Data
public class LoginReqDto {
    private String username;

    private String password;

    public LoginReqDto(String password, String username) {
        this.username = username;
        this.password = password;

    }

}
