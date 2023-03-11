package ru.work.graduatework.Old_mapper;

import ru.work.graduatework.Old_Entity.Image;
import ru.work.graduatework.dto.ImageDto;

public class ImageMapper {

    public static ImageDto toDto(Image image) {
        return new ImageDto(image.getId(), image.getIdAds(), image.getImage(), image.getAds());
    }

    public static Image toEntity(ImageDto imageDto) {
        Image image = new Image();
        image.setId(imageDto.getId());
        image.setIdAds(imageDto.getIdAds());
        image.setImage(imageDto.getImage());
        image.setAds(imageDto.getAds());
        return image;
    }

}
