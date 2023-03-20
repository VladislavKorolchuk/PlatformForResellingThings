package ru.work.graduatework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import ru.work.graduatework.mapper.AdsCommentMapper;
import ru.work.graduatework.mapper.AdsMapper;
import ru.work.graduatework.repository.AdsRepository;
import ru.work.graduatework.service.AdsService;
import ru.work.graduatework.service.ImageService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collection;

@RestController()
@RequiredArgsConstructor
@CrossOrigin(value = {"http://localhost:3000"})
@RequestMapping("/ads")
public class AdsController {

    private final Logger logger = LoggerFactory.getLogger(AdsController.class);

    private final AdsRepository adsRepository;
    private final AdsService adsService;
    private final AdsMapper adsMapper;
    private final AdsCommentMapper adsCommentMapper;
    private final ImageService imageService;


    @Operation(
            operationId = "getALLAds",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Ads.class))))
            },
            tags = "Объявления")
    @GetMapping    // Получить объявление
    public ResponseWrapper<AdsDto> getAllAds() { //сделано
        logger.info("Current Method is - getAds");
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
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)   // сделано
    public ResponseEntity<AdsDto> addAds(@RequestParam MultipartFile adsImage, CreateAdsDto createAdsDto) throws IOException {
        logger.info("Current Method is - addAds");
        return ResponseEntity.ok(adsMapper.toDto(adsService.addAds(createAdsDto, adsImage)));
    }

    //    @Operation(summary = "getComments", operationId = "getComments",
//            responses = {@ApiResponse(responseCode = "200", description = "OK",
//                    content = @Content(
//                            mediaType = MediaType.ALL_VALUE,
//                            array = @ArraySchema(schema = @Schema(implementation = ResponseWrapperCommentDto.class)))),
//                    @ApiResponse(responseCode = "404",
//                            description = "Not Found")}, tags = "Объявления")
    @GetMapping("/{ad_pk}/comments")  // сделано
    public ResponseWrapper<CommentDto> getComments(@PathVariable("ad_pk") long ad_pk) {
        return ResponseWrapper.of(adsCommentMapper.toDto(adsService.getComments(ad_pk)));
    }

    @Operation(summary = "addComments", operationId = "addComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CommentDto.class))), //Comments.класс
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @PostMapping("/{ad_pk}/comments") // сделано
    public ResponseEntity<CommentDto> addComments(@PathVariable("ad_pk") long ad_pk, @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(adsCommentMapper.toDto(adsService.addAdsComments(ad_pk, commentDto)));
    }

    @Operation(summary = "getFullAd", operationId = "getAds",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            schema = @Schema(implementation = FullAdsDto.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),}, tags = "Объявления")
    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDto> getFullAd(@PathVariable("id") long id) { //сделано
        logger.info("Current Method is - getFullAd");

        return ResponseEntity.ok(adsMapper.toFullAdsDto(adsService.getAdsById(id)));
    }

    @Operation(summary = "removeAds", operationId = "removeAds",
            responses = {@ApiResponse(responseCode = "204", description = "No Content", content = {}),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
            }, tags = "Объявления")
    @DeleteMapping("/{adId}")   // Убрать рекламу
    public ResponseEntity<Void> removeAds(@PathVariable("adId") long adId) {
        logger.info("Current Method is - removeAds");
        adsService.removeAds(adId);
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
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @PatchMapping("/{adId}")   // сделано
    public ResponseEntity<AdsDto> updateAds(@PathVariable("adId") long adId, @RequestBody CreateAdsDto createAdsDto) {
        logger.info("Current Method is - updateAds");
        return ResponseEntity.ok(adsMapper.toDto(adsService.updateAds(adId, createAdsDto)));
    }

    @Operation(summary = "updateAdsImage", operationId = "updateAds", description = "updateAdsImage", tags = "Объявления")
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAdsImage(@PathVariable("id") long id,
                                            @RequestBody MultipartFile image) {
        adsService.updateAdsImage(id, image);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "getComments", operationId = "getComments_1",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            schema = @Schema(implementation = CommentDto.class))), // Comment.class
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
            }, tags = "Объявления")
    @GetMapping("/{ad_pk}/comments/{id}")   // сделано
    public ResponseEntity<CommentDto> getComments(@PathVariable("ad_pk") long ad_pk,
                                                  @PathVariable("id") long id) {
        logger.info("Current Method is - getCommentsId");
        return ResponseEntity.ok(adsCommentMapper.toDto(adsService.getAdsComment(ad_pk, id)));
    }

    @Operation(summary = "deleteComments", operationId = "deleteComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @DeleteMapping("/{ad_pk}/comments/{id}")   // сделано
    public ResponseEntity<CommentDto> deleteCommentsId(@PathVariable("ad_pk") long ad_pk,
                                                       @PathVariable("id") long id) {
        logger.info("Current Method is - deleteCommentsId");
        adsService.deleteAdsComment(ad_pk, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "updateComments", operationId = "updateComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            schema = @Schema(implementation = Ads.class))), //  Comment.class
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @PatchMapping("/{ad_pk}/comments/{id}")    // сделано
    public ResponseEntity<CommentDto> updateCommentsId(@PathVariable("ad_pk") long adPk,
                                                       @PathVariable("id") int id,
                                                       @RequestBody CommentDto commentDto) {
        logger.info("Current Method is - updateCommentsId");
        return ResponseEntity.ok(adsCommentMapper.toDto(adsService.updateComments(adPk, id, adsCommentMapper.toEntity(commentDto))));

    }

    @Operation(summary = "getAdsMe", operationId = "getAdsMeUsingGET",
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Ads.class)))), //ResponseWrapperAds
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @GetMapping("/me")   // Получить рекламу
    public ResponseWrapper<AdsDto> getAdsMe() { // сделано
        logger.info("Current Method is - getAdsMe");
        Collection<Ads> listAds = adsService.getAdsMe();
        return ResponseWrapper.of(adsMapper.toDto(listAds));
    }

    @GetMapping(value = "/image/{id}", produces = {MediaType.IMAGE_PNG_VALUE}) //сделано
    public ResponseEntity<byte[]> getAdsImage(@PathVariable("id") long id,
                                              @NotNull @RequestBody MultipartFile image) {
        return ResponseEntity.ok(imageService.getImageById(id).getData());
    }
}

