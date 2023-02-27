package ru.work.graduatework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.service.UsersService;


@RestController
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @Operation(summary = "Получить пользователя",
            operationId = "getUser_1",
            responses = {@ApiResponse
                    (responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.ALL_VALUE,
                                    schema = @Schema(implementation = Users.class)
                            )),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            }, tags = "USER"
    )
    @GetMapping("/users/me")
    public String getUsers() {
        return usersService.getUsers();
    }

    @Operation(summary = "Установить пароль",
            operationId = "setPassword",
            responses = {@ApiResponse
                    (responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Users.class) // пользователи - нужен пароль
                            )),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            }, tags = "USER"
    )
    @PostMapping("/users/set_password")
    public String setPassword() {
        return usersService.setPassword();
    }

    @Operation(summary = "Обновить данные пользователя",
            operationId = "updateUser",
            responses = {@ApiResponse
                    (responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.ALL_VALUE,
                                    schema = @Schema(implementation = Users.class)
                            )),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            }, tags = "USER"
    )
    @PatchMapping("/users/me")
    public String updateUser() {               //   required: true
        return usersService.updateUser();
    }

    @Operation(summary = "Обновление изображение пользователя",
            responses = {@ApiResponse
                    (responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.MULTIPART_FORM_DATA_VALUE
                            )),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found"
                    )
            }, tags = "USER"
    )
    @PatchMapping("/users/me/image")
    public String updateUserImage() {
        return usersService.updateUserImage();
    }
}
