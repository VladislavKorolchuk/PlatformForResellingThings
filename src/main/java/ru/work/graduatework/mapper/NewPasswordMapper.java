package ru.work.graduatework.mapper;

import ru.work.graduatework.Entity.NewPassword;
import ru.work.graduatework.dto.NewPasswordDto;

public class NewPasswordMapper {

    public static NewPasswordDto toDto(NewPassword newPassword) {
        return new NewPasswordDto(newPassword.getCurrentPassword(), newPassword.getNewPassword());
    }

    public static NewPassword toEntity(NewPasswordDto newPasswordDto) {
        NewPassword newPassword = new NewPassword();
        newPassword.setCurrentPassword(newPasswordDto.getCurrentPassword());
        newPassword.setNewPassword(newPasswordDto.getNewPassword());
        return newPassword;
    }

}
