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
import ru.work.graduatework.mapper.AdsMapper;
import ru.work.graduatework.repository.AdsRepository;
import ru.work.graduatework.service.AdsService;

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


    @Operation(
            operationId = "getALLAds",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Ads.class))))
            },
            tags = "Объявления")
    @GetMapping    // Получить объявление
    public ResponseWrapper<AdsDto> getAllAds() {
        logger.info("Current Method is - getAds");
        Collection<Ads> adsCollection = adsService.getAllAds();
        return ResponseWrapper.of(adsMapper.toDto(adsCollection));
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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)   // Добавить объявления
    public ResponseEntity<AdsDto> addAds(@RequestParam MultipartFile adsImage, CreateAdsDto createAdsDto) throws IOException {
        logger.info("Current Method is - addAds");
//        AdsDto adsDto = adsService.addAds(createAdsDto, adsImage);
//        if (adsDto == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        } else
//            return ResponseEntity.ok(adsDto); //нужен Created
        return null;
    }

//    @Operation(summary = "getComments", operationId = "getComments",
//            responses = {@ApiResponse(responseCode = "200", description = "OK",
//                    content = @Content(
//                            mediaType = MediaType.ALL_VALUE,
//                            array = @ArraySchema(schema = @Schema(implementation = ResponseWrapperCommentDto.class)))),
//                    @ApiResponse(responseCode = "404",
//                            description = "Not Found")}, tags = "Объявления")
    @GetMapping("/{ad_pk}/comments")  // Получить комментарии
    public ResponseEntity<?> getComments(@PathVariable("ad_pk") Integer add_pk) {
//        logger.info("Current Method is - getCommentsId");
//        ResponseWrapperCommentDto comment = adsService.getComments(add_pk);
//        if (comment == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        return ResponseEntity.ok(comment);
    return     ResponseEntity.ok().build();
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
    @PostMapping("/{ad_pk}/comments") // Добавить Комментарии
    public ResponseEntity<CommentDto> addComments(@PathVariable("ad_pk") int ad_pk, @RequestBody CommentDto commentDto) {
//        logger.info("Current Method is - addComments");
//        CommentDto comment = adsService.addComments(ad_pk, commentDto);
//        if (comment == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        } else return ResponseEntity.ok(comment);
      return   ResponseEntity.ok().build();
    }

    @Operation(summary = "getFullAd", operationId = "getAds",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            schema = @Schema(implementation = FullAdsDto.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),}, tags = "Объявления")
    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDto> getFullAd(@PathVariable int id) {
        logger.info("Current Method is - getFullAd");
//        FullAdsDto fullAdsDto = adsService.getF(id);
//        return ResponseEntity.ok(fullAdsDto);
     return  ResponseEntity.ok().build();
    }

    @Operation(summary = "removeAds", operationId = "removeAds",
            responses = {@ApiResponse(responseCode = "204", description = "No Content", content = {}),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
            }, tags = "Объявления")
    @DeleteMapping("/{id}")   // Убрать рекламу
    public void removeAds(@PathVariable int id) { // параметры
        logger.info("Current Method is - removeAds");
        adsService.removeAds(id);
        return;
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
    @PatchMapping("/{id}")   // Обновить рекламу
    public ResponseEntity<AdsDto> updateAds(@PathVariable long id, @RequestBody CreateAdsDto createAds) {
        logger.info("Current Method is - updateAds");
//        AdsDto adsDtoUpdate = adsService.updateAds(id, createAds);
//        if (adsDtoUpdate == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        } else return ResponseEntity.ok(adsDtoUpdate);
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
    @GetMapping("/{ad_pk}/comments/{id}")   // Получить комментарии по id
    public ResponseEntity<CommentDto> getCommentsId(@PathVariable("ad_pk") Integer ad_pk,
                                                    @PathVariable("id") Integer id) {
        logger.info("Current Method is - getCommentsId");
//        CommentDto comment = adsService.getCommentsId(ad_pk, id);
//        if (comment == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "deleteComments", operationId = "deleteComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @DeleteMapping("/{ad_pk}/comments/{id}")   // Удалить комментарии по id
    public ResponseEntity<CommentDto> deleteCommentsId(@PathVariable("ad_pk") String ad_pk,
                                                       @PathVariable int id) {
        logger.info("Current Method is - deleteCommentsId");
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
    @PatchMapping("/{ad_pk}/comments/{id}")    // Обновление комментария по id
    public ResponseEntity<CommentDto> updateCommentsId(@PathVariable("ad_pk") String adPk,
                                                       @PathVariable int id,
                                                       @RequestBody CommentDto commentDto) {
        logger.info("Current Method is - updateCommentsId");
//        CommentDto comment = adsService.updateComments(adPk,id,commentDto);
//        if (comment == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
        return ResponseEntity.ok().build();

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
    public ResponseWrapper<AdsDto> getAdsMe() { // параметры нужно разобрать и дописать
        logger.info("Current Method is - getAdsMe");
        Collection<Ads> adsCollection = adsService.getAllAds();
         return ResponseWrapper.of(adsMapper.toDto(adsCollection));
    }
}

