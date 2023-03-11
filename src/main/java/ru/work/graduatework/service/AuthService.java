package ru.work.graduatework.service;

import ru.work.graduatework.Old_DTO.RegisterReqDto;
import ru.work.graduatework.dto.Role;

public interface AuthService {

    boolean login(String userName, String password);

    boolean register(RegisterReqDto registerReqDto, Role role);

}
