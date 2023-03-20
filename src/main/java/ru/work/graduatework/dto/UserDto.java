package ru.work.graduatework.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import ru.work.graduatework.Entity.Ads;
import ru.work.graduatework.Entity.Image;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Data
public class UserDto {

    private long id;

    @NotBlank
    @Size(min = 3)
    private String firstName;

    @NotBlank
    @Size(min = 3)
    private String lastName;

    @Schema(example = "user@user.ru")
    private String email;

    private String phone;

    private String city;

    private String regDate;

    private String image;
}
