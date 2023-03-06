package ru.work.graduatework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.dto.ImageDto;
import ru.work.graduatework.dto.NewPasswordDto;
import ru.work.graduatework.dto.UserDto;
import ru.work.graduatework.service.UsersService;


@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/users")
public class UsersController {

    private final Logger logger = LoggerFactory.getLogger(UsersController.class);
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
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUsers() {
        logger.info("Current Method is - getUsers");
        return ResponseEntity.status(HttpStatus.OK).build();
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
    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@RequestBody NewPasswordDto newPasswordDto) {
        logger.info("Current Method is - setPassword");
        return ResponseEntity.status(HttpStatus.OK).build();
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
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        logger.info("Current Method is - updateUser");
        return ResponseEntity.status(HttpStatus.OK).build();
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
    @PatchMapping("/me/image")
    public ResponseEntity<UserDto> updateUserImage(@RequestBody ImageDto imageDto) {
        logger.info("Current Method is - updateUserImage");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
