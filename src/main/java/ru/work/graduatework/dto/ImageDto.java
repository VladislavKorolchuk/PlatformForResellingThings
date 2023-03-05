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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAds() {
        return idAds;
    }

    public void setIdAds(Long idAds) {
        this.idAds = idAds;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Ads getAds() {
        return ads;
    }

    public void setAds(Ads ads) {
        this.ads = ads;
    }
}
