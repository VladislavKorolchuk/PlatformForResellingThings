package ru.work.graduatework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.work.graduatework.Entity.Ads;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.dto.AdsDto;
import ru.work.graduatework.dto.CreateAdsDto;
import ru.work.graduatework.dto.FullAdsDto;

@Mapper(componentModel = "spring")
public interface AdsMapper extends MapperScheme<AdsDto, Ads> {

    String PUTH_IMAGE = "/ads/image/";

    @Mapping(target = "id", source = "pk")
    @Mapping(target = "author.id", source = "author")
    @Mapping(target = "image", ignore = true)
    Ads toEntity(AdsDto dto);

    @Mapping(target = "pk", ignore = true)
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "image", source = "entity.image", qualifiedByName = "imageMapping")
    AdsDto toDto(Ads entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "image", ignore = true)
    Ads toEntity(CreateAdsDto dto);

    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "phone", source = "author.phone")
    @Mapping(target = "email", source = "author.email")
    @Mapping(target = "image", source = "entity.image", qualifiedByName = "imageMapping")
    @Mapping(target = "pk", source = "id")
    FullAdsDto toFullAdsDto(Ads entity);

    @Named("imageMapping")
    default String imageMapping(Image image) {
       if (image == null) {
            return null;
        }
        return "/ads/image/" +image.getId();
    }

}
