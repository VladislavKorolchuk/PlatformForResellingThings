package ru.work.graduatework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.work.graduatework.Entity.Ads;
import ru.work.graduatework.dto.AdsDto;
import ru.work.graduatework.dto.CreateAdsDto;


@Mapper(componentModel = "spring")
public interface AdsMapper extends MapperScheme<AdsDto, Ads> {

    String PUTH_IMAGE = "/ads/image/";

    @Mapping(target = "id", source = "pk")
    @Mapping(target = "author.id", source = "author")
    @Mapping(target = "image", ignore = true)
    Ads toEntity(AdsDto dto);

    @Override
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "image", source = "entity.image", qualifiedByName = "imageMapping")
    default AdsDto toDto(Ads entity) {
        return null;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "image", ignore = true)
    Ads toEntity(CreateAdsDto dto);

//    @Mapping(target = "authorFirstName", source = "authorFirstName")
//    @Mapping(target = "authorLastName", source = "authorLastName")
//    @Mapping(target = "phone", source = "author.phone")
//    @Mapping(target = "email", source = "author.email")
//    @Mapping(target = "image", source = "entity.image", qualifiedByName = "imageMapping")
//    @Mapping(target = "pk", source = "id")
//    FullAdsDto toFullAdsDto(Ads entity);

//    @Named("imageMapping")
//    default byte[] imageMapping(Image image) {
//        if (image == null) {
//            byte[] byteImage = new byte[1];
//            return byteImage;
//        }
//        return image.getData();
//    }
}
