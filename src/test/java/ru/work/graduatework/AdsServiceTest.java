package ru.work.graduatework;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.work.graduatework.Entity.Ads;
import ru.work.graduatework.Entity.Comment;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.dto.AdsDto;
import ru.work.graduatework.dto.CommentDto;
import ru.work.graduatework.dto.CreateAdsDto;
import ru.work.graduatework.dto.repository.AdsRepository;
import ru.work.graduatework.dto.repository.CommentRepository;
import ru.work.graduatework.mapper.CommentMapper;
import ru.work.graduatework.service.impl.AdsServiceImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
public class AdsServiceTest {
    @Autowired
    public AdsRepository adsRepository;
    @Autowired
    public CommentRepository commentRepository;
    @Autowired
    public AdsServiceImpl adsServiceImpl;
    Ads ads = new Ads();
    Integer author = 1;
    Integer pk = 1;
    Integer price = 1;
    String title = "1";
    String image = "1";
    List<Image> images = new ArrayList<>();
    Collection<Comment> commentCollection = new HashSet<>();
    Collection<Ads> adsCollection = new HashSet<>();
    String description = "1";

    @BeforeEach
    public void setUp() {

        ads.setAuthor(author);
        ads.setPk(pk);
        ads.setPrice(price);
        ads.setTitle(title);
        ads.setImage(image);

        ads.setImages(images);
        ads.setCommentCollection(commentCollection);
        adsRepository.save(ads);
    }

    @AfterEach
    public void postUp() {
        adsRepository.deleteById(ads.getPk());
    }

    @Test
    public void getAdsTest() {
        Collection<AdsDto> adsDto = this.adsServiceImpl.getAds();
        Assertions
                .assertThat(adsDto).hasSize(1);
    }

    @Test
    public void addAdsTest() {
        AdsDto request = new AdsDto(author, image, pk, price, title);
        CreateAdsDto createAdsDto = new CreateAdsDto(description, price, title);
        AdsDto result = adsServiceImpl.addAds(createAdsDto, image);

        Assertions
                .assertThat(request.getAuthor()).isEqualTo(result.getAuthor());
        Assertions
                .assertThat(request.getImage()).isEqualTo(result.getImage());
        Assertions
                .assertThat(request.getPrice()).isEqualTo(result.getPrice());
        Assertions
                .assertThat(request.getTitle()).isEqualTo(result.getTitle());

    }

    @Test
    public void removeAdsTest() {
        CreateAdsDto createAdsDto2 = new CreateAdsDto("description", 2, "title");
        AdsDto part2 = adsServiceImpl.addAds(createAdsDto2, "image");
        adsServiceImpl.removeAds(part2.getPk());
        Collection<AdsDto> adsDto = this.adsServiceImpl.getAds();

        Assertions
                .assertThat(adsDto).hasSize(1);
    }

    @Test
    public void addCommentsTest() {
        CommentDto commentDto = new CommentDto(author, "createdAt", pk, "Text");

        Comment comment = CommentMapper.toEntity(commentDto);
        ads.getCommentCollection().add(comment);
        commentRepository.save(comment);

       CommentDto actual = adsServiceImpl.addComments(pk, commentDto);
        Assertions
                .assertThat(commentDto).isEqualTo(actual);

        commentRepository.delete(comment);
    }
}