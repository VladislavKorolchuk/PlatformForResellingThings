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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.dto.NewPasswordDto;
import ru.work.graduatework.dto.UserDto;
import ru.work.graduatework.mapper.UserMapper;
import ru.work.graduatework.repository.UsersRepository;
import ru.work.graduatework.service.ImageService;
import ru.work.graduatework.service.UsersService;
import java.security.Principal;


@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/users")
public class UsersController {

    private final Logger logger = LoggerFactory.getLogger(UsersController.class);
    private final UsersService usersService;

    private final ImageService imageService;
    private final UsersRepository usersRepository;

    private  UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
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
    public UserDto getUsers() {

        logger.info("Class UsersController, current method is - getUsers");
        return  userMapper.toDto(usersService.getUsers());

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
    public ResponseEntity<NewPasswordDto> setPassword(@RequestBody NewPasswordDto newPasswordDto, Principal principal) {
        logger.info("Class UsersController, current method is - setPassword");
        Users currentUser = usersService.getUser(principal.getName());
        //TODO: Проверить на фронте
        if (this.passwordEncoder.matches(newPasswordDto.getCurrentPassword(), currentUser.getCurrentPassword())) {
            currentUser.setNewPassword(this.passwordEncoder.encode(newPasswordDto.getNewPassword()));
            this.usersRepository.save(currentUser);
        }
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
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateUserImage(@RequestParam MultipartFile image) {
        logger.info("Class UsersController, current method is - updateUserImage");
//        Users users = usersService.getUser(principal.getName());
//        ImageDto image = usersService.updateUserImage(1, imageDto);
//        return ResponseEntity.ok(image.getData());
        return ResponseEntity.ok().body(usersService.updateUserImage(image));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser (@PathVariable("id") long id) {
        Users user = usersService.getUserById(id);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

}
