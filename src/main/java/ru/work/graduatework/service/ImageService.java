package ru.work.graduatework.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.work.graduatework.Entity.Ad;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.Entity.User;
import ru.work.graduatework.repository.AdRepository;
import ru.work.graduatework.repository.ImageRepository;
import ru.work.graduatework.repository.UserRepository;

import javax.transaction.Transactional;
import java.io.*;

@Transactional
@RequiredArgsConstructor
@Service
public class ImageService {

    private final Logger logger = LoggerFactory.getLogger(ImageService.class);
    private final AdRepository adRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    public Image addAdsImage(Ad ad, MultipartFile imageFile) throws IOException {
        logger.info("Current Method is - addAdsImage ");
//        Path path = Path.of(imageDir, ad.getId() + "." + getExtensions(
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
//        User users = usersRepository.findById(id).orElseThrow();
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

    /**
     * @param 'MultipartFile multipartFile' Input parameter
     *                       <br> Is used entity User {@link Image} </br
     *                       <br> Is used repository {@link ImageRepository#save(Object)} </br>
     *                       Uses method {@link  UserService#updateUserImage(MultipartFile, String)}    getImageById(int)}
     * @return {@link ru.work.graduatework.Entity.Image}
     * @author Korolchuk Vladislav
     */
    public Image uploadImage(MultipartFile multipartFile) throws IOException {
        Image image = new Image();
        image.setData(multipartFile.getBytes());
        image.setFileSize(multipartFile.getSize());
        image.setMediaType(multipartFile.getContentType());
        image.setData(multipartFile.getBytes());
        return imageRepository.save(image);
    }

    /**
     * @param 'id image' Input parameter
     *            <br> Is used entity User {@link User} </br
     *            <br> Is used repository {@link UserRepository#save(Object)} </br>
     *            Uses method {@link  ru.work.graduatework.controller.UsersController#getImageById(int)}
     *            Uses method {@link  ru.work.graduatework.controller.AdsController#getAdsImage(int, MultipartFile)}
     * @return {@link User}
     * @author Korolchuk Vladislav
     */
    public Image getImageById(int id) {

        return imageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

}