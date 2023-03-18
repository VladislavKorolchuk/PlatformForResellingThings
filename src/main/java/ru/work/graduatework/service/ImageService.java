package ru.work.graduatework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.Ads;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.repository.AdsRepository;
import ru.work.graduatework.repository.ImageRepository;
import ru.work.graduatework.repository.UsersRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class ImageService {

    @Value("${path.to.image.folder}")
    private String imageDir;

    private final AdsRepository adsRepository;
    private final ImageRepository imageRepository;
    private final UsersRepository usersRepository;

    public ImageService(AdsRepository adsRepository, ImageRepository imageRepository,
                        UsersRepository usersRepository) {
        this.adsRepository = adsRepository;
        this.imageRepository = imageRepository;
        this.usersRepository = usersRepository;
    }

    public Image addAdsImage(Integer id, MultipartFile imageFile) throws IOException {
        Ads ads = adsRepository.findById(id).orElseThrow();
        Path path = Path.of(imageDir, id + "." + getExtensions(
                Objects.requireNonNull(imageFile.getOriginalFilename())));
        if (!Files.exists(path.getParent())) {
            Files.createDirectory(path.getParent());
        }
        Files.deleteIfExists(path);
        try (
                InputStream is = imageFile.getInputStream();
                OutputStream os = Files.newOutputStream(path, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Image image = new Image();
        image.setFilePath(path.toString());
        image.setMediaType(imageFile.getContentType());
        image.setFileSize(imageFile.getSize());
      //  image.setData(imageFile.getBytes());
//        image.s(ads);
        imageRepository.save(image);
        return image;
    }

    public Image addUserImage(Integer id, MultipartFile imageFile) throws IOException {
        Users users = usersRepository.findById(id).orElseThrow();
        Path path = Path.of(imageDir, id + "." + getExtensions(
                Objects.requireNonNull(imageFile.getOriginalFilename())));
        if (!Files.exists(path.getParent())) {
            Files.createDirectory(path.getParent());
        }
        Files.deleteIfExists(path);
        try (
                InputStream is = imageFile.getInputStream();
                OutputStream os = Files.newOutputStream(path, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
        Image image = new Image();
        image.setFilePath(path.toString());
        image.setMediaType(imageFile.getContentType());
        image.setFileSize(imageFile.getSize());
     //   image.setData(imageFile.getBytes());
//        image.setUsers(users);
        imageRepository.save(image);
        return image;
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
