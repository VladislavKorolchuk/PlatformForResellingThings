package ru.work.graduatework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.dto.*;
import ru.work.graduatework.mapper.UserMapper;
import ru.work.graduatework.service.ImageService;
import ru.work.graduatework.service.UsersService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/users")
public class UsersController {

    private final Logger logger = LoggerFactory.getLogger(UsersController.class);
    private final UsersService usersService;

    private final ImageService imageService;
    private final UserMapper userMapper;
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
    public ResponseWrapper<UserDto> getUsers() {
        logger.info("Class UsersController, current method is - getUsers");
        Collection<Users> users = usersService.getUsers();
        return ResponseWrapper.of(userMapper.toDto(users));
    }

    @Operation(summary = "getUser", description = "getUser")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") long id) {
        Users user = usersService.getUserById(id);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @Operation(summary = "addUser", description = "addUser",operationId = "addUser",tags = "USER")
    @PostMapping
    public ResponseEntity<CreateUserDto> addUser(@RequestBody CreateUserDto createUserDto) {
        Users user = usersService.createUser(userMapper.createUserDtoToEntity(createUserDto));
        return ResponseEntity.ok(userMapper.toCreateUserDto(user));
    }
    @Operation(summary = "getImageById", description = "getImageById",operationId = "getImageById",tags = "USER")
    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getImageById(@PathVariable("id") int id) {
        return ResponseEntity.ok(imageService.getImageById(id).getData());
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
    public ResponseEntity<NewPasswordDto> setPassword(@Valid @RequestBody NewPasswordDto newPasswordDto) {
        usersService.newPassword(newPasswordDto.getNewPassword(), newPasswordDto.getCurrentPassword());
        return ResponseEntity.ok(newPasswordDto);
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
    public ResponseEntity<UserDto>  updateUser(@RequestBody UserDto userDto) {// сделано
        Users user = userMapper.toEntity(userDto);
        return ResponseEntity.ok(userMapper.toDto(usersService.updateUser(user)));
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
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateUserImage(@RequestBody MultipartFile image) {
        return ResponseEntity.ok().body(usersService.updateUserImage(image));
    }

    @Operation(summary = "updateRole", description = "updateRole",tags = "USER")
    @PutMapping("/{id}/updateRole")
    public ResponseEntity<UserDto> updateRole(@PathVariable("id") long id, Role role) {
        UserDto userDto = userMapper.toDto(usersService.updateRole(id, role));
        return ResponseEntity.ok(userDto);
    }
}
