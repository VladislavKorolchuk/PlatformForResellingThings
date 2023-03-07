package ru.work.graduatework.mapper;

import ru.work.graduatework.Entity.Ads;
import ru.work.graduatework.dto.AdsDto;

public class AdsMapper {

    public static AdsDto toDto(Ads ads) {
        return new AdsDto(ads.getAuthor(), ads.getImage(), ads.getPk(), ads.getPrice(), ads.getTitle());
    }

    public static Ads toEntity(AdsDto adsDto) {
        Ads ads = new Ads();
        ads.setAuthor(adsDto.getAuthor());
        ads.setImage(adsDto.getImage());
        ads.setPk(adsDto.getPk());
        ads.setPrice(adsDto.getPrice());
        ads.setTitle(adsDto.getTitle());
        return ads;
    }

}