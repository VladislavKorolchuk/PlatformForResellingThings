package ru.work.graduatework.Old_DTO;

import lombok.Data;

@Data
public class LoginReqDto {

    private String password;
    private String username;

    public LoginReqDto(String password, String username) {
        this.password = password;
        this.username = username;
    }

}
