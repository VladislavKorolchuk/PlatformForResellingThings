package ru.work.graduatework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.User;
import ru.work.graduatework.dto.CreateUserDto;
import ru.work.graduatework.dto.NewPasswordDto;
import ru.work.graduatework.dto.Role;
import ru.work.graduatework.dto.UserDto;
import ru.work.graduatework.mapper.UserMapper;
import ru.work.graduatework.service.ImageService;
import ru.work.graduatework.service.UserService;


@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/users")
public class UsersController {

  private final Logger logger = LoggerFactory.getLogger(UsersController.class);
  private final UserService userService;
  private final ImageService imageService;
  private final UserMapper userMapper;


  //  ----- Анастасия сделай плиз @Operation ------------
  @Operation(summary = "Getting user by ID",tags = "USER")
  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUser(@PathVariable("id") long id) {
    return ResponseEntity.ok(userMapper.toDto(userService.getUserById(id)));
  }

  @Operation(summary = "Get a logged in user",
      operationId = "getUsers",
      responses = {@ApiResponse
          (responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = MediaType.ALL_VALUE,
                  schema = @Schema(implementation = User.class)
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
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return userMapper.toDto(userService.getUsers(authentication.getName()));
  }

  //  ----- Анастасия сделай плиз @Operation ------------
  @Operation(summary = "Add user",tags = "USER")
  @PostMapping
  public ResponseEntity<CreateUserDto> addUser(@Valid @RequestBody CreateUserDto createUserDto) {
    User user = userService.createUser(userMapper.createUserDtoToEntity(createUserDto));
    return ResponseEntity.ok(userMapper.toCreateUserDto(user));
  }

  @Operation(summary = "Password change",
      operationId = "setPassword",
      responses = {@ApiResponse
          (responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = MediaType.APPLICATION_JSON_VALUE,
                  schema = @Schema(implementation = User.class) // пользователи - нужен пароль
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
    logger.info("Current method is - setPassword");
      userService.newPassword(newPasswordDto.getNewPassword(), newPasswordDto.getCurrentPassword());
      return ResponseEntity.ok(newPasswordDto);
  }

  @Operation(summary = "Updating the user image",
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
    logger.info("Current method is - updateUserImage");
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return ResponseEntity.ok().body(userService.updateUserImage(image, authentication.getName()));
  }


  @Operation(summary = "Updating the user",responses = {@ApiResponse
          (responseCode = "200",
                  description = "OK"),
          @ApiResponse(
                  responseCode = "404",
                  description = "Not Found"
          )
  },tags = "USER")
  @PatchMapping("/me")
  public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return ResponseEntity.ok(
        userMapper.toDto(userService.updateUser(userDto, authentication.getName())));
  }

  @Operation(summary = "Updating user the Role ",operationId = "UpdateUsersRole", responses = {
          @ApiResponse(
                  responseCode = "200",
                  description = "Role is changed"
          ),
          @ApiResponse(
                  responseCode = "404",
                  description = "Not Found"
          )
  },tags = "USER")
  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping("/{id}/updateRole")
  public ResponseEntity<UserDto> updateRole(@PathVariable("id") long id, Role role) {
    UserDto userDto = userMapper.toDto(userService.updateRole(id, role));
    return ResponseEntity.ok(userDto);
  }


  @Operation(summary = "Updating image of ID ",operationId = "UpdateImageById" ,responses = {
          @ApiResponse(
                  responseCode = "200",
                  description = "Image is updated"
          ),
          @ApiResponse(
                  responseCode = "404",
                  description = "Not Found"
          )},tags = "USER")
  @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
  public ResponseEntity<byte[]> getImageById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(imageService.getImageById(id).getData());
  }

}
