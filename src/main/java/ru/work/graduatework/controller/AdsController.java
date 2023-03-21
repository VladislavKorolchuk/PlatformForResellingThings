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
import ru.work.graduatework.mapper.AdsMapper;
import ru.work.graduatework.mapper.CommentMapper;
import ru.work.graduatework.repository.AdsRepository;
import ru.work.graduatework.service.AdsService;
import ru.work.graduatework.service.ImageService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController()
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
public class AdsController {

    private final Logger logger = LoggerFactory.getLogger(AdsController.class);
    private final AdsRepository adsRepository;
    private final AdsService adsService;
    private final AdsMapper adsMapper;

    private CommentMapper commentMapper;

    private final ImageService imageService;


    @Operation(
            operationId = "getALLAds",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Ads.class))))
            },
            tags = "ADS")
    @GetMapping
    public ResponseWrapper<AdsDto> getAllAds() {

        logger.info("Current Method is - getAllAds");
        return ResponseWrapper.of(adsMapper.toDto(adsService.getAllAds()));

    }

    @Operation(summary = "addAds", operationId = "addAds",
            responses = {@ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Ads.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "ADS")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAds(@Parameter(description = "New Ad Data")
                                         @RequestPart("image") MultipartFile adsImage,
                                         @Valid @RequestPart("properties") CreateAdsDto createAdsDto) {
        logger.info("Current Method is - addAds");
        return ResponseEntity.ok(adsMapper.toDto(adsService.addAds(createAdsDto, adsImage)));
    }

    @Operation(summary = "getComments", operationId = "getComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ResponseWrapperCommentDto.class)))),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found")}, tags = "ADS")
    @GetMapping("/{ad_pk}/comments")
    public ResponseWrapper<AdsCommentDto> getComments(@PathVariable("ad_pk") long adPk) {
        logger.info("Current Method is - getCommentsId");
        return ResponseWrapper.of(commentMapper.toDto(adsService.getComments(adPk)));
    }

    @Operation(summary = "addComments", operationId = "addComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdsCommentDto.class))), //Comments.класс
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "ADS")
    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDto> getFullAd(@PathVariable int id) {
        logger.info("Current Method is - getFullAd");
        FullAdsDto fullAdsDto = adsService.getFullAd(id);
        return ResponseEntity.ok(fullAdsDto);
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
        adsService.removeAdsById(adId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "updateAds", operationId = "updateAds",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            schema = @Schema(implementation = Ads.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "ADS")
    @PatchMapping("/{adId}")
    public ResponseEntity<AdsDto> updateAds(@P @PathVariable("adId") Integer adId, @RequestBody CreateAdsDto createAds) {

        logger.info("Current Method is - updateAds");
        return ResponseEntity.ok(adsMapper.toDto(adsService.updateAds(adId, createAds)));

    }

    @Operation(summary = "getComments", operationId = "getComments_1",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            schema = @Schema(implementation = AdsCommentDto.class))), // Comment.class
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
            }, tags = "ADS")
    @GetMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<AdsCommentDto> getAdsComment(@PathVariable("ad_pk") long ad_pk,
                                                       @PathVariable("id") long id) {
        logger.info("Current Method is - getCommentsId");
        return ResponseEntity.ok(commentMapper.toDto(adsService.getAdsComment(ad_pk, id)));
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
        adsService.deleteAdsComment(ad_pk, id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "updateComments", operationId = "updateComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            schema = @Schema(implementation = Ads.class))), //  Comment.class
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "ADS")
    @PatchMapping("/{ad_pk}/comments/{id}")    // Обновление комментария по id
    public ResponseEntity<AdsCommentDto> updateComments(@PathVariable("ad_pk") int adPk,
                                                        @PathVariable int id,
                                                        @RequestBody AdsCommentDto adsCommentDto) {
        logger.info("Current Method is - updateCommentsId");
        return ResponseEntity.ok(commentMapper.toDto(adsService.updateComments(
                adPk, id, commentMapper.toEntity(adsCommentDto))));

    }

    @Operation(summary = "getAdsMe", operationId = "getAdsMe",
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Ads.class)))), //ResponseWrapperAds
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "ADS")
    @GetMapping("/me")
    public ResponseWrapper<AdsDto> getAdsMe() {
        logger.info("Current Method is - getAdsMe");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseWrapper.of(adsMapper.toDto(adsService.getAdsMe(authentication.getName())));
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAdsImage(@PathVariable("id") int id, @NotNull @RequestBody MultipartFile image) {

        adsService.updateAdsImage(id, image);
        return ResponseEntity.ok().build();

    }

    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getAdsImage(@PathVariable("id") int id, @NotNull @RequestBody MultipartFile image) {
        return ResponseEntity.ok(imageService.getImageById(id).getData());
    }

}

