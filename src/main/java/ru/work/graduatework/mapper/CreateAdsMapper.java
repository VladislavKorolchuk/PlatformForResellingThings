package ru.work.graduatework.mapper;

import ru.work.graduatework.Entity.CreateAds;
import ru.work.graduatework.dto.CreateAdsDto;

public class CreateAdsMapper {

    public static CreateAdsDto toDto(CreateAds createAds){
        return new CreateAdsDto(createAds.getDescription(), createAds.getPrice(), createAds.getTitle());
    }

    public static CreateAds toEntity(CreateAdsDto createAdsDto){
        CreateAds createAds = new CreateAds();
        createAds.setDescription(createAdsDto.getDescription());
        createAds.setPrice(createAdsDto.getPrice());
        createAds.setTitle(createAdsDto.getTitle());
        return createAds;
    }
}
