package ru.work.graduatework.service;


import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.Image;

import java.io.IOException;

public interface ImageService {
    Image addImage(Integer id, MultipartFile imageFile) throws IOException;
}
