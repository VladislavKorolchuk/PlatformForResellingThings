package ru.work.graduatework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import ru.work.graduatework.dto.LoginReq;
import ru.work.graduatework.dto.RegisterReq;
import ru.work.graduatework.dto.Role;
import ru.work.graduatework.service.AuthService;

import static ru.work.graduatework.dto.Role.USER;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;
@Operation(summary = "login",operationId = "login",
responses = {@ApiResponse(responseCode = "200",description = "OK",content =@Content(mediaType = MediaType.ALL_VALUE,schema = @Schema(implementation = Object.class))),
@ApiResponse(responseCode = "404",
description = "Not Found"),
@ApiResponse(responseCode = "401", description = "Unauthorized",content = {}),
@ApiResponse(responseCode = "403",description = "Forbidden",content ={})},tags = "Авторизация")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq req) {
        if (authService.login(req.getUsername(), req.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
    @Operation(summary = "register",operationId = "register",
            responses = {@ApiResponse(responseCode = "404",description = "Not Found"),
                    @ApiResponse(responseCode = "201",
                            description = "Created",content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized",content = {}),
                    @ApiResponse(responseCode = "403",description = "Forbidden",content ={})},tags = "Авторизация")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterReq req) {
        Role role = req.getRole() == null ? USER : req.getRole();
        if (authService.register(req, role)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
