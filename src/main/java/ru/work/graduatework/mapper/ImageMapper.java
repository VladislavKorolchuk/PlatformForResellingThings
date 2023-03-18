package ru.work.graduatework.mapper;

import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.dto.ImageDto;

public class ImageMapper {

    public static ImageDto toDto(Image image) {
        return new ImageDto(image.getId(), image.getFilePath(), image.getFileSize(), image.getMediaType(), image.getData().getBytes());
    }

    public static Image toEntity(ImageDto imageDto) {
        Image image = new Image();
        image.setId(imageDto.getId());
        image.setFilePath(imageDto.getFilePath());
        image.setFileSize(imageDto.getFileSize());
        image.setMediaType(imageDto.getMediaType());
       // image.setData(imageDto.getData());
        return image;
    }

}
