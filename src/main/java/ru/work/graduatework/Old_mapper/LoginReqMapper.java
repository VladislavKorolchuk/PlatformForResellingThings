package ru.work.graduatework.Old_mapper;

import ru.work.graduatework.Old_Entity.LoginReq;
import ru.work.graduatework.Old_DTO.LoginReqDto;

public class LoginReqMapper {

    public static LoginReqDto toDto(LoginReq loginReq) {
        return new LoginReqDto(loginReq.getPassword(), loginReq.getUsername());
    }

    public static LoginReq toEntity(LoginReqDto loginReqDto) {
        LoginReq loginReq = new LoginReq();
        loginReq.setPassword(loginReqDto.getPassword());
        loginReq.setUsername(loginReqDto.getUsername());
        return loginReq;
    }

}
