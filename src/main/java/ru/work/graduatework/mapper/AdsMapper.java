package ru.work.graduatework.mapper;

import ru.work.graduatework.Entity.Ads;
import ru.work.graduatework.dto.AdsDto;

public class AdsMapper {

    public static AdsDto toDto(Ads ads) {
        return new AdsDto(ads.getAuthor(), ads.getImage(), ads.getPk(), ads.getPrice(), ads.getTitle(), ads.getDescription());
    }

    public static Ads toEntity(AdsDto adsDto) {
        Ads ads = new Ads();
        ads.setAuthor(adsDto.getAuthor());
        ads.setImage(ads.getImage());
        ads.setPk(adsDto.getPk());
        ads.setPrice(adsDto.getPrice());
        ads.setTitle(adsDto.getTitle());
        ads.setDescription(adsDto.getDescription());
        return ads;
    }

}
