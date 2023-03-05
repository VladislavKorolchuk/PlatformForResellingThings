package ru.work.graduatework.mapper;

import ru.work.graduatework.Entity.ResponseWrapperAds;
import ru.work.graduatework.dto.ResponseWrapperAdsDto;

public class ResponseWrapperAdsMapper {
    public static ResponseWrapperAdsDto toDto(ResponseWrapperAds responseWrapperAds) {
        return new ResponseWrapperAdsDto(responseWrapperAds.getCount(), responseWrapperAds.getResults());
    }

    public static ResponseWrapperAds toEntity(ResponseWrapperAdsDto responseWrapperAdsDto) {
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds();
        responseWrapperAds.setCount(responseWrapperAdsDto.getCount());
        responseWrapperAds.setResults(responseWrapperAdsDto.getResults());
        return responseWrapperAds;
    }
}
