package ru.work.graduatework.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.work.graduatework.Entity.Ads;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.repository.AdsRepository;
import ru.work.graduatework.repository.ImageRepository;
import ru.work.graduatework.repository.UsersRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
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

    public Image uploadImage(MultipartFile imageFile) throws IOException {
        Image image = new Image();

        image.setData(imageFile.getBytes());
        image.setFileSize(imageFile.getSize());
        image.setMediaType(imageFile.getContentType());
        image.setData(imageFile.getBytes());
        return imageRepository.save(image);
    }
    public Image getImageById(long id) {
        return imageRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
    @SneakyThrows
    public ResponseEntity<byte[]> updateAdsImage(long id, MultipartFile image) {
        if (image == null) {
            return ResponseEntity.badRequest().build();
        }

        Image img = imageRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        img.setFileSize(image.getSize());
        img.setMediaType(image.getContentType());
        img.setData(image.getBytes());

        return ResponseEntity.ok(imageRepository.save(img).getData());
    }
    }
