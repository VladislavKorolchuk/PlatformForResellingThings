package ru.work.graduatework.service;

import ru.work.graduatework.dto.RegisterReq;
import ru.work.graduatework.dto.Role;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, Role role);
}
