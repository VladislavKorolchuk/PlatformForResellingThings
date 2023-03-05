package ru.work.graduatework.dto;

import lombok.Data;
import ru.work.graduatework.Entity.Ads;

@Data
public class ImageDto {
    private Long id;
    private Long idAds;
    private String image;
    private Ads ads;

    public ImageDto(Long id, Long idAds, String image, Ads ads) {
        this.id = id;
        this.idAds = idAds;
        this.image = image;
        this.ads = ads;
    }
}
