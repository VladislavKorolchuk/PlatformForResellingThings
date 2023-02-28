package ru.work.graduatework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    @Operation(summary = "updateAdsImage",operationId = "updateAdsImage",
            responses = {@ApiResponse(responseCode = "200",description = "OK",content =@Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,schema = @Schema(type = "array",format = "byte"))),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found")},tags = "Изображения")
    @PostMapping("/{id}")
    public ResponseEntity<?> updateAdsImage() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
