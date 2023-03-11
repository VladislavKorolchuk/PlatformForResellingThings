package ru.work.mapper;

import ru.work.graduatework.Entity.ResponseWrapperAds;
import ru.work.graduatework.Old_DTO.ResponseWrapperAdsDto;

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
