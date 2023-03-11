package ru.work.graduatework.Old_DTO;

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
