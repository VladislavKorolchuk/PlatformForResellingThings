package ru.work.graduatework.mapper;

import ru.work.graduatework.Entity.Ads;
import ru.work.graduatework.Entity.Users;
import ru.work.graduatework.dto.AdsDto;
import ru.work.graduatework.repository.UsersRepository;

public class AdsMapper1 {
    UsersRepository repository;


        public static AdsDto toDto(Ads ads) {
        return new AdsDto(ads.getAuthor().getId(), ads.getImage().getFilePath(), ads.getId(), ads.getPrice(), ads.getTitle(), ads.getDescription());

        //, users.getAdsCollection()
    }

//    public static Ads toEntity(AdsDto adsDto) {
//        Ads ads = new Ads();
//        ads.setAuthor(adsDto.getAuthor());
//        ads.setImage(adsDto.getImage());
//        ads.setLastName(adsDto.getLastName());
//        ads.setPhone(adsDto.getPhone());
//        ads.setEmail(adsDto.getEmail());
//        return ads;
//    }
}
