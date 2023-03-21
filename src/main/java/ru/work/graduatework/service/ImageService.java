package ru.work.graduatework.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.work.graduatework.Entity.Ads;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.repository.AdsRepository;
import ru.work.graduatework.repository.ImageRepository;
import ru.work.graduatework.repository.UsersRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Transactional
@RequiredArgsConstructor
@Service
public class ImageService {

    private final Logger logger = LoggerFactory.getLogger(ImageService.class);
    private final AdsRepository adsRepository;
    private final ImageRepository imageRepository;
    private final UsersRepository usersRepository;

    public Image addAdsImage(Ads ads, MultipartFile imageFile) throws IOException {
        logger.info("Current Method is - addAdsImage ");
//        Path path = Path.of(imageDir, ads.getId() + "." + getExtensions(
//                Objects.requireNonNull(imageFile.getOriginalFilename())));
//        if (!Files.exists(path.getParent())) {
//            Files.createDirectory(path.getParent());
//        }
//        Files.deleteIfExists(path);
//        try (
//                InputStream is = imageFile.getInputStream();
//                OutputStream os = Files.newOutputStream(path, CREATE_NEW);
//                BufferedInputStream bis = new BufferedInputStream(is, 1024);
//                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
//        ) {
//            bis.transferTo(bos);
//        }
//        Image image = new Image();
//        image.setFilePath(path.toString());
//        image.setMediaType(imageFile.getContentType());
//        image.setFileSize(imageFile.getSize());
//        //  image.setData(imageFile.getBytes())
////        image.setAds
//        imageRepository.save(image);
        return null;
    }

    public Image addUserImage(long id, MultipartFile imageFile) throws IOException {
//        Users users = usersRepository.findById(id).orElseThrow();
//        Path path = Path.of(imageDir, id + "." + getExtensions(
//                Objects.requireNonNull(imageFile.getOriginalFilename())));
//        if (!Files.exists(path.getParent())) {
//            Files.createDirectory(path.getParent());
//        }
//        Files.deleteIfExists(path);
//        try (
//                InputStream is = imageFile.getInputStream();
//                OutputStream os = Files.newOutputStream(path, CREATE_NEW);
//                BufferedInputStream bis = new BufferedInputStream(is, 1024);
//                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
//        ) {
//            bis.transferTo(bos);
//        }
//        Image image = new Image();
//        image.setFilePath(path.toString());
//        image.setMediaType(imageFile.getContentType());
//        image.setFileSize(imageFile.getSize());
//        //   image.setData(imageFile.getBytes());
////        image.setUsers(users);
//        imageRepository.save(image);
        return null;
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


    // Uses method - updateUserImage    service - UsersService
    public Image uploadImage(MultipartFile multipartFile) throws IOException {
        Image image = new Image();
        image.setData(multipartFile.getBytes());
        image.setFileSize(multipartFile.getSize());
        image.setMediaType(multipartFile.getContentType());
        image.setData(multipartFile.getBytes());
        return imageRepository.save(image);
    }

    // Uses method - getImageById    controller - UserController
    // Uses method - getAdsImage    controller - AdsController
    public Image getImageById (int id) {
        return imageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }




}