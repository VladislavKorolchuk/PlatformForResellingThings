package ru.work.graduatework.mapper;

import ru.work.graduatework.Entity.Ads;
import ru.work.graduatework.dto.AdsDto;

public class AdsMapper {

    public static AdsDto toDto(Ads ads) {
        return new AdsDto(ads.getUser().getId(), ads.getPk(), ads.getPrice(), ads.getTitle(), ads.getImage().getFilePath(), ads.getDescription());
    }

    public static Ads toEntity(AdsDto adsDto) {
        Ads ads = new Ads();
//        ads.setAuthor(adsDto.getAuthor());  мб найти по id и вернуть как user
        ads.setImage(ads.getImage());
        ads.setPk(adsDto.getPk());
        ads.setPrice(adsDto.getPrice());
        ads.setTitle(adsDto.getTitle());
        ads.setDescription(adsDto.getDescription());
        return ads;
    }

}
