package ru.work.graduatework.mapper;

import ru.work.graduatework.Entity.FullAds;
import ru.work.graduatework.dto.FullAdsDto;

public class FullAdsMapper {
    public static FullAdsDto toDto(FullAds fullAds) {
        return new FullAdsDto(fullAds.getAuthorFirstName(), fullAds.getAuthorLastName(), fullAds.getDescription(), fullAds.getEmail(),
                fullAds.getImage(), fullAds.getPhone(), fullAds.getPk(), fullAds.getPrice(), fullAds.getTitle());
    }

    public static FullAds toEntity(FullAdsDto fullAdsDto) {
        FullAds fullAds = new FullAds();
        fullAds.setAuthorFirstName(fullAdsDto.getAuthorFirstName());
        fullAds.setAuthorLastName(fullAdsDto.getAuthorFirstName());
        fullAds.setDescription(fullAdsDto.getDescription());
        fullAds.setEmail(fullAdsDto.getEmail());
        fullAds.setImage(fullAdsDto.getImage());
        fullAds.setPhone(fullAdsDto.getPhone());
        fullAds.setPk(fullAdsDto.getPk());
        fullAds.setPrice(fullAdsDto.getPrice());
        fullAds.setTitle(fullAdsDto.getTitle());
        return fullAds;
    }
}
