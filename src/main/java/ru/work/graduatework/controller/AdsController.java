package ru.work.graduatework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.work.graduatework.dto.LoginReq;
import ru.work.graduatework.service.AdsService;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdsController {
    private final AdsService adsService;

    @Operation(
            operationId = "getALLAds",
            responses = {@ApiResponse(responseCode = "200", description = "OK")}, tags = "Объявления")
    @GetMapping()    // Получить объявление
    public ResponseEntity<?> getAds() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "addAds", operationId = "addAds",
            responses = {@ApiResponse(responseCode = "201", description = "Created", content = {}),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @PostMapping()   // Добавить объявления
    public ResponseEntity<?> addAds() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "getComments", operationId = "getComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK", content = {}),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found")}, tags = "Объявления")
    @GetMapping("/{ad_pk}/comments")  // Получить комментарии
    public ResponseEntity<?> getComments() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "addComments", operationId = "addComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK", content = {}),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @PostMapping("/{ad_pk}/comments")  // Добавить Комментарии
    public ResponseEntity<?> addComments() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "getFullAd", operationId = "getAds",
            responses = {@ApiResponse(responseCode = "200", description = "OK", content = {}),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),}, tags = "Объявления")
    @GetMapping("/{id}")   // Получить полную рекламу
    public ResponseEntity<?> getFullAd() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "removeAds", operationId = "removeAds",
            responses = {@ApiResponse(responseCode = "204", description = "No Content", content = {}),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
            }, tags = "Объявления")
    @DeleteMapping("/ads/{id}")   // Убрать рекламу
    public ResponseEntity<?> removeAds() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "updateAds", operationId = "updateAds",
            responses = {@ApiResponse(responseCode = "200", description = "OK", content = {}),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @PatchMapping("/ads/{id}")   // Обновить рекламу
    public ResponseEntity<?> updateAds() {
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @Operation(summary = "getComments", operationId = "getComments_1",
            responses = {@ApiResponse(responseCode = "200", description = "OK", content = {}),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
            }, tags = "Объявления")
    @GetMapping("/ads/{ad_pk}/comments/{id}")   // Получить комментарии по id
    public ResponseEntity<?> getCommentsId() {
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @Operation(summary = "deleteComments", operationId = "deleteComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @DeleteMapping("/ads/{ad_pk}/comments/{id}")   // Удалить комментарии по id
    public ResponseEntity<?> deleteCommentsId() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "updateComments", operationId = "updateComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @PatchMapping("/ads/{ad_pk}/comments/{id}")    // Обновление комментария по id
    public ResponseEntity<?> updateCommentsId() {
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @Operation(summary = "updateComments", operationId = "updateComments",
            responses = {@ApiResponse(responseCode = "200", content = {}),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @GetMapping("/ads/me")   // Получить рекламу
    public ResponseEntity<?> getAdsMe() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
