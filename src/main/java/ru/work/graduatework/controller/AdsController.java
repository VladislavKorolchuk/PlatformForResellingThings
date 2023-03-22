package ru.work.graduatework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import liquibase.pro.packaged.P;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.*;
import ru.work.graduatework.dto.*;
import ru.work.graduatework.mapper.AdMapper;
import ru.work.graduatework.mapper.CommentMapper;
import ru.work.graduatework.repository.AdRepository;
import ru.work.graduatework.service.AdService;
import ru.work.graduatework.service.ImageService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController()
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
public class AdsController {

    private final Logger logger = LoggerFactory.getLogger(AdsController.class);
    private final AdRepository adRepository;
    private final AdService adservice;
    private final AdMapper adMapper;

    private CommentMapper commentMapper;

    private final ImageService imageService;


    @Operation(
            operationId = "getALLAds",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Ad.class))))
            },
            tags = "ADS")
    @GetMapping
    public ResponseWrapper<AdDto> getAllAds() {

        logger.info("Current Method is - getAllAds");
        return ResponseWrapper.of(adMapper.toDto(adservice.getAllAds()));

    }

    @Operation(summary = "addAds", operationId = "addAds",
            responses = {@ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Ad.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "ADS")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto> addAds(@Parameter(description = "New Ad Data")
                                         @RequestPart("image") MultipartFile adsImage,
                                        @Valid @RequestPart("properties") CreateAdDto createAdDto) {
        logger.info("Current Method is - addAds");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(adMapper.toDto(adservice.addAds(createAdDto, adsImage,authentication.getName())));
    }

    @Operation(summary = "getComments", operationId = "getComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ResponseWrapperCommentDto.class)))),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found")}, tags = "ADS")
    @GetMapping("/{ad_pk}/comments")
    public ResponseWrapper<AdCommentDto> getComments(@PathVariable("ad_pk") long adPk) {
        logger.info("Current Method is - getCommentsId");
        return ResponseWrapper.of(commentMapper.toDto(adservice.getComments(adPk)));
    }

    @Operation(summary = "addComments", operationId = "addComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdCommentDto.class))), //Comments.класс
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "ADS")
    @GetMapping("/{id}")
    public ResponseEntity<FullAdDto> getFullAd(@PathVariable int id) {
        logger.info("Current Method is - getFullAd");
        FullAdDto fullAdDto = adservice.getFullAd(id);
        return ResponseEntity.ok(fullAdDto);
    }

    @Operation(summary = "removeAds", operationId = "removeAds",
            responses = {@ApiResponse(responseCode = "204", description = "No Content", content = {}),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
            }, tags = "ADS")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAds(@PathVariable("id") int adId) {
        logger.info("Current Method is - removeAds");
        adservice.removeAdsById(adId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "updateAds", operationId = "updateAds",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            schema = @Schema(implementation = Ad.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "ADS")
    @PatchMapping("/{adId}")
    public ResponseEntity<AdDto> updateAds(@P @PathVariable("adId") Integer adId, @RequestBody CreateAdDto createAds) {

        logger.info("Current Method is - updateAds");
        return ResponseEntity.ok(adMapper.toDto(adservice.updateAds(adId, createAds)));

    }

    @Operation(summary = "getComments", operationId = "getComments_1",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            schema = @Schema(implementation = AdCommentDto.class))), // Comment.class
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
            }, tags = "ADS")
    @GetMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<AdCommentDto> getAdsComment(@PathVariable("ad_pk") long ad_pk,
                                                      @PathVariable("id") long id) {
        logger.info("Current Method is - getCommentsId");
        return ResponseEntity.ok(commentMapper.toDto(adservice.getAdsComment(ad_pk, id)));
    }

    @Operation(summary = "deleteComments", operationId = "deleteComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "ADS")
    @DeleteMapping("/{ad_pk}/comments/{id}")   // Удалить комментарии по id
    public ResponseEntity<HttpStatus> deleteAdsComment(@PathVariable("ad_pk") long ad_pk,
                                                       @PathVariable("id") long id) {
        logger.info("Current Method is - deleteCommentsId");
        adservice.deleteAdsComment(ad_pk, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "updateComments", operationId = "updateComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            schema = @Schema(implementation = Ad.class))), //  Comment.class
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "ADS")
    @PatchMapping("/{ad_pk}/comments/{id}")    // Обновление комментария по id
    public ResponseEntity<AdCommentDto> updateComments(@PathVariable("ad_pk") int adPk,
                                                       @PathVariable int id,
                                                       @RequestBody AdCommentDto adCommentDto) {
        logger.info("Current Method is - updateCommentsId");
        return ResponseEntity.ok(commentMapper.toDto(adservice.updateComments(
                adPk, id, commentMapper.toEntity(adCommentDto))));

    }

    @Operation(summary = "getAdsMe", operationId = "getAdsMe",
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Ad.class)))), //ResponseWrapperAds
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "ADS")
    @GetMapping("/me")
    public ResponseWrapper<AdDto> getAdsMe() {
        logger.info("Current Method is - getAdsMe");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseWrapper.of(adMapper.toDto(adservice.getAdsMe(authentication.getName())));
    }

    @Operation(tags = "ADS")
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAdsImage(@PathVariable("id") int id, @NotNull @RequestBody MultipartFile image) {

        adservice.updateAdsImage(id, image);
        return ResponseEntity.ok().build();

    }
    @Operation(tags = "ADS")
    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getAdsImage(@PathVariable("id") int id, @NotNull @RequestBody MultipartFile image) {
        return ResponseEntity.ok(imageService.getImageById(id).getData());
    }

}

