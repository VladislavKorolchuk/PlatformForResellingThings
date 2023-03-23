package ru.work.graduatework.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.Entity.User;
import ru.work.graduatework.repository.ImageRepository;
import ru.work.graduatework.repository.UserRepository;

import javax.transaction.Transactional;
import java.io.*;

@Transactional
@RequiredArgsConstructor
@Service
public class ImageService {

    private final Logger logger = LoggerFactory.getLogger(ImageService.class);
    private final ImageRepository imageRepository;

    /**
     * @param 'MultipartFile multipartFile' Input parameter
     *                       <br> Is used entity User {@link Image} </br
     *                       <br> Is used repository {@link ImageRepository#save(Object)} </br>
     *                       Uses method {@link  UserService#updateUserImage(MultipartFile, String)}    getImageById(int)}
     * @return {@link ru.work.graduatework.Entity.Image}
     * @author Korolchuk Vladislav
     */
    public Image uploadImage(MultipartFile multipartFile) throws IOException {

        logger.info("Current method is - uploadImage");
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
    public Image getImageById(long id) {

        logger.info("Current method is - getImageById");
        return imageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

}