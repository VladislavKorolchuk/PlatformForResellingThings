package ru.work.graduatework.dto;

import lombok.Data;
import ru.work.graduatework.Entity.Ads;

import java.util.List;

@Data
public class ResponseWrapperAdsDto {
    private Integer count;
    private List<Ads> results;

    public ResponseWrapperAdsDto(Integer count, List<Ads> results) {
        this.count = count;
        this.results = results;
    }
}
