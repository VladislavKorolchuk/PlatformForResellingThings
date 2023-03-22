package ru.work.graduatework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.*;
import ru.work.graduatework.dto.*;
import ru.work.graduatework.mapper.AdMapper;
import ru.work.graduatework.mapper.CommentMapper;
import ru.work.graduatework.service.AdsService;
import ru.work.graduatework.service.ImageService;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collection;

@RestController()
@RequiredArgsConstructor
@CrossOrigin(value = {"http://localhost:3000"})
@RequestMapping("/ads")
public class AdController {

    private final Logger logger = LoggerFactory.getLogger(AdController.class);

    private final AdsService adsService;
    private final AdMapper adMapper;
    private final CommentMapper commentMapper;
    private final ImageService imageService;


    @Operation(
            operationId = "getALLAds",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Ad.class))))
            },
            tags = "Объявления")
    @GetMapping    // Получить объявление
    public ResponseWrapper<AdDto> getAllAds() { //сделано
        logger.info("Current Method is - getAds");
        return ResponseWrapper.of(adMapper.toDto(adsService.getAllAds()));
    }

    @Operation(summary = "addAds", operationId = "addAds",
            responses = {@ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Ad.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)   // сделано
    public ResponseEntity<AdDto> addAds(@RequestParam MultipartFile adsImage, CreateAdDto createAdDto) throws IOException {
        logger.info("Current Method is - addAds");
        return ResponseEntity.ok(adMapper.toDto(adsService.addAds(createAdDto, adsImage)));
    }

    @Operation(summary = "getComments", operationId = "getComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ResponseWrapperCommentDto.class)))),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found")}, tags = "Объявления")
    @GetMapping("/{ad_pk}/comments")  // сделано
    public ResponseWrapper<AdCommentDto> getComments(@PathVariable("ad_pk") long ad_pk) {
        logger.info("Current Method is - getComments - controller");
        return ResponseWrapper.of(commentMapper.toDto(adsService.getComments(ad_pk)));
    }

//    @DeleteMapping("/{ad_pk}/comments/{id}")
//    public ResponseEntity<HttpStatus> deleteAdsComment(@PathVariable("ad_pk") long adPk, @PathVariable("id") long id) {
//        logger.info("Current Method is - deleteAdsComment - controller");
//        adsService.deleteAdsComment(adPk, id);
//        return ResponseEntity.ok(HttpStatus.OK);
//    }

    @Operation(summary = "addComments", operationId = "addComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AdCommentDto.class))), //Comments.класс
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @PostMapping("/{ad_pk}/comments") // сделано
    public ResponseEntity<AdCommentDto> addComments(@PathVariable("ad_pk") long ad_pk, @RequestBody AdCommentDto commentDto) {
        logger.info("Current Method is - addComments - controller");
        return ResponseEntity.ok(commentMapper.toDto(adsService.addAdsComments(ad_pk, commentDto)));
    }

    @Operation(summary = "getFullAd", operationId = "getAds",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            schema = @Schema(implementation = FullAdDto.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),}, tags = "Объявления")
    @GetMapping("/{id}")
    public ResponseEntity<FullAdDto> getFullAd(@PathVariable("id") long id) { //сделано
        logger.info("Current Method is - getFullAd - controller");
        return ResponseEntity.ok(adMapper.toFullAdsDto(adsService.getAdsById(id)));
    }

    @Operation(summary = "removeAds", operationId = "removeAds",
            responses = {@ApiResponse(responseCode = "204", description = "No Content", content = {}),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
            }, tags = "Объявления")
    @DeleteMapping("/{adId}")   // Убрать рекламу
    public ResponseEntity<Void> removeAds(@PathVariable("adId") long adId) {
        logger.info("Current Method is - removeAds - controller");
        adsService.removeAds(adId);
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
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @PatchMapping("/{adId}")   // сделано
    public ResponseEntity<AdDto> updateAds(@PathVariable("adId") Long adId, @RequestBody CreateAdDto createAdDto) {
        logger.info("Current Method is - updateAds - controller");
        return ResponseEntity.ok(adMapper.toDto(adsService.updateAds(adId, createAdDto)));
    }

    @Operation(summary = "updateAdsImage", operationId = "updateAds", description = "updateAdsImage", tags = "Объявления")
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAdsImage(@PathVariable("id") long id,
                                            @RequestBody MultipartFile image) {
        logger.info("Current Method is - updateAdsImage - controller");
        adsService.updateAdsImage(id, image);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "getComments", operationId = "getComments_1",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            schema = @Schema(implementation = AdCommentDto.class))), // Comment.class
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
            }, tags = "Объявления")
    @GetMapping("/{ad_pk}/comments/{id}")   // сделано
    public ResponseEntity<AdCommentDto> getComments(@PathVariable("ad_pk") long ad_pk,
                                                    @PathVariable("id") long id) {
        logger.info("Current Method is - getCommentsId - controller");
        return ResponseEntity.ok(commentMapper.toDto(adsService.getAdsComment(ad_pk, id)));
    }

    @Operation(summary = "deleteComments", operationId = "deleteComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @DeleteMapping("/{ad_pk}/comments/{id}")   // сделано
    public ResponseEntity<AdCommentDto> deleteCommentsId(@PathVariable("ad_pk") long ad_pk,
                                                         @PathVariable("id") long id) {
        logger.info("Current Method is - deleteCommentsId - controller");
        adsService.deleteAdsComment(ad_pk, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "updateComments", operationId = "updateComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            schema = @Schema(implementation = Ad.class))), //  Comment.class
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @PatchMapping("/{ad_pk}/comments/{id}")    // сделано
    public ResponseEntity<AdCommentDto> updateCommentsId(@PathVariable("ad_pk") long adPk,
                                                         @PathVariable("id") int id,
                                                         @RequestBody AdCommentDto commentDto) {
        logger.info("Current Method is - updateCommentsId - controller");
        return ResponseEntity.ok(commentMapper.toDto(adsService.updateComments(adPk, id, commentMapper.toEntity(commentDto))));

    }

    @Operation(summary = "getAdsMe", operationId = "getAdsMeUsingGET",
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Ad.class)))), //ResponseWrapperAds
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @GetMapping("/me")   // Получить рекламу
    public ResponseWrapper<AdDto> getAdsMe() { // сделано
        logger.info("Current Method is - getAdsMe - controller");
        Collection<Ad> listAds = adsService.getAdsMe();
        return ResponseWrapper.of(adMapper.toDto(listAds));
    }

    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_PNG_VALUE}) //сделано
    public ResponseEntity<byte[]> getAdsImage(@PathVariable("id") long id,
                                              @NotNull @RequestBody MultipartFile image) {
        logger.info("Current Method is - getAdsImage - controller");
        return ResponseEntity.ok(imageService.getImageById(id).getData());
    }
}

