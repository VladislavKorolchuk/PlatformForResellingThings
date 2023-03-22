package ru.work.graduatework.dto;

import lombok.Data;
import ru.work.graduatework.Entity.Ad;

import java.util.List;

@Data
public class ResponseWrapperAdDto {

    private Integer count;
    private List<Ad> results;

//    public ResponseWrapperAdsDto(Integer count, List<Ads> results) {
//        this.count = count;
//        this.results = results;
//    }

}
