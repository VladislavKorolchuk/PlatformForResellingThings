package ru.work.graduatework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.work.graduatework.Entity.Ad;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.dto.AdDto;
import ru.work.graduatework.dto.CreateAdDto;
import ru.work.graduatework.dto.FullAdDto;

@Mapper(componentModel = "spring")
public interface AdMapper extends MapperScheme<AdDto, Ad> {

    @Mapping(target = "id", source = "pk")
    @Mapping(target = "author.id", source = "author")
    @Mapping(target = "image", ignore = true)
    Ad toEntity(AdDto dto);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "image", source = "entity.image", qualifiedByName = "imageMapping")
    AdDto toDto(Ad entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "image", ignore = true)
    Ad toEntity(CreateAdDto dto);

    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "phone", source = "author.phone")
    @Mapping(target = "email", source = "author.email")
    @Mapping(target = "image", source = "entity.image", qualifiedByName = "imageMapping")
    @Mapping(target = "pk", source = "id")
    FullAdDto toFullAdsDto(Ad entity);

    @Named("imageMapping")
    default String imageMapping(Image image) {
       if (image == null) {
            return null;
        }
        return "/ads/image/" +image.getId();
    }

}
