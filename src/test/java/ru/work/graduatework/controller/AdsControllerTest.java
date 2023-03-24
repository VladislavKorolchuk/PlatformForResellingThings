package ru.work.graduatework.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import ru.work.graduatework.Entity.Ad;
import ru.work.graduatework.Entity.Comment;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.Entity.User;
import ru.work.graduatework.dto.AdCommentDto;
import ru.work.graduatework.dto.AdDto;
import ru.work.graduatework.dto.CreateAdDto;
import ru.work.graduatework.dto.FullAdDto;
import ru.work.graduatework.dto.Role;
import ru.work.graduatework.mapper.AdMapper;
import ru.work.graduatework.mapper.AdMapperImpl;
import ru.work.graduatework.mapper.CommentMapper;
import ru.work.graduatework.mapper.CommentMapperImpl;
import ru.work.graduatework.repository.AdRepository;
import ru.work.graduatework.repository.CommentRepository;
import ru.work.graduatework.repository.ImageRepository;
import ru.work.graduatework.repository.UserRepository;
import ru.work.graduatework.service.AdService;
import ru.work.graduatework.service.ImageService;

@ContextConfiguration(classes = {AdsController.class})
@ExtendWith(SpringExtension.class)
class AdsControllerTest {
    @MockBean
    private AdMapper adMapper;

    @MockBean
    private AdService adService;

    @Autowired
    private AdsController adsController;

    @MockBean
    private CommentMapper commentMapper;

    @MockBean
    private ImageService imageService;

    /**
     * Method under test: {@link AdsController#getAllAds()}
     */
    @Test
    void testGetAllAds() throws Exception {
        when(adService.getAllAds()).thenReturn(new ArrayList<>());
        when(adMapper.toDto((Collection<Ad>) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ads");
        MockMvcBuilders.standaloneSetup(adsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"count\":0,\"result\":[]}"));
    }

    /**
     * Method under test: {@link AdsController#getComments(long)}
     */
    @Test
    void testGetComments() throws Exception {
        when(adService.getComments(anyLong())).thenReturn(new ArrayList<>());
        when(commentMapper.toDto((Collection<Comment>) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ads/{ad_pk}/comments", 1L);
        MockMvcBuilders.standaloneSetup(adsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"count\":0,\"result\":[]}"));
    }

    /**
     * Method under test: {@link AdsController#getComments(long)}
     */
    @Test
    void testGetComments2() throws Exception {
        when(adService.getComments(anyLong())).thenReturn(new ArrayList<>());
        when(commentMapper.toDto((Collection<Comment>) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/ads/{ad_pk}/comments", 1L);
        getResult.accept("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adsController).build().perform(getResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406));
    }

    /**
     * Method under test: {@link AdsController#getFullAd(int)}
     */
    @Test
    void testGetFullAd() throws Exception {
        FullAdDto fullAdDto = new FullAdDto();
        fullAdDto.setAuthorFirstName("Jane");
        fullAdDto.setAuthorLastName("Doe");
        fullAdDto.setDescription("The characteristics of someone or something");
        fullAdDto.setEmail("jane.doe@example.org");
        fullAdDto.setImage("Image");
        fullAdDto.setPhone("4105551212");
        fullAdDto.setPk(1);
        fullAdDto.setPrice(1);
        fullAdDto.setTitle("Dr");
        when(adService.getFullAd(anyLong())).thenReturn(fullAdDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ads/{id}", 1);
        MockMvcBuilders.standaloneSetup(adsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"image\":\"Image\",\"authorLastName\":\"Doe\",\"authorFirstName\":\"Jane\",\"phone\":\"4105551212\",\"price\":1,"
                                        + "\"description\":\"The characteristics of someone or something\",\"pk\":1,\"title\":\"Dr\",\"email\":\"jane.doe"
                                        + "@example.org\"}"));
    }

    /**
     * Method under test: {@link AdsController#getFullAd(int)}
     */
    @Test
    void testGetFullAd2() throws Exception {
        FullAdDto fullAdDto = new FullAdDto();
        fullAdDto.setAuthorFirstName("Jane");
        fullAdDto.setAuthorLastName("Doe");
        fullAdDto.setDescription("The characteristics of someone or something");
        fullAdDto.setEmail("jane.doe@example.org");
        fullAdDto.setImage("Image");
        fullAdDto.setPhone("4105551212");
        fullAdDto.setPk(1);
        fullAdDto.setPrice(1);
        fullAdDto.setTitle("Dr");
        when(adService.getFullAd(anyLong())).thenReturn(fullAdDto);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/ads/{id}", 1);
        getResult.accept("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adsController).build().perform(getResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406));
    }

    /**
     * Method under test: {@link AdsController#updateAds(Integer, CreateAdDto)}
     */
    @Test
    void testUpdateAds() throws Exception {
        Image image = new Image();
        image.setData("AAAAAAAA".getBytes("UTF-8"));
        image.setFileSize(3L);
        image.setId(123L);
        image.setMediaType("Media Type");

        User user = new User();
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setImage(image);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setPhone("4105551212");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setRegDate(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setRole(Role.USER);

        Image image1 = new Image();
        image1.setData("AAAAAAAA".getBytes("UTF-8"));
        image1.setFileSize(3L);
        image1.setId(123L);
        image1.setMediaType("Media Type");

        Ad ad = new Ad();
        ad.setAuthor(user);
        ad.setDescription("The characteristics of someone or something");
        ad.setId(123L);
        ad.setImage(image1);
        ad.setPrice(1);
        ad.setTitle("Dr");
        when(adService.updateAds(anyInt(), (CreateAdDto) any())).thenReturn(ad);
        when(adMapper.toDto((Ad) any()))
                .thenReturn(new AdDto(1, "Image", 1, 1, "Dr", "The characteristics of someone or something"));

        CreateAdDto createAdDto = new CreateAdDto();
        createAdDto.setDescription("The characteristics of someone or something");
        createAdDto.setPrice(1);
        createAdDto.setTitle("Dr");
        String content = (new ObjectMapper()).writeValueAsString(createAdDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/ads/{adId}", 123)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(adsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"author\":1,\"image\":\"Image\",\"pk\":1,\"price\":1,\"title\":\"Dr\"}"));
    }

    /**
     * Method under test: {@link AdsController#getAdsComment(long, long)}
     */
    @Test
    void testGetAdsComment() throws Exception {
        Image image = new Image();
        image.setData("AAAAAAAA".getBytes("UTF-8"));
        image.setFileSize(3L);
        image.setId(123L);
        image.setMediaType("Media Type");

        User user = new User();
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setImage(image);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setPhone("4105551212");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setRegDate(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setRole(Role.USER);

        Image image1 = new Image();
        image1.setData("AAAAAAAA".getBytes("UTF-8"));
        image1.setFileSize(3L);
        image1.setId(123L);
        image1.setMediaType("Media Type");

        Ad ad = new Ad();
        ad.setAuthor(user);
        ad.setDescription("The characteristics of someone or something");
        ad.setId(123L);
        ad.setImage(image1);
        ad.setPrice(1);
        ad.setTitle("Dr");

        Image image2 = new Image();
        image2.setData("AAAAAAAA".getBytes("UTF-8"));
        image2.setFileSize(3L);
        image2.setId(123L);
        image2.setMediaType("Media Type");

        User user1 = new User();
        user1.setCity("Oxford");
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setImage(image2);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setPhone("4105551212");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setRegDate(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        user1.setRole(Role.USER);

        Comment comment = new Comment();
        comment.setAd(ad);
        comment.setAuthor(user1);
        comment.setCreatedAt("Jan 1, 2020 8:00am GMT+0100");
        comment.setId(123L);
        comment.setText("Text");
        when(adService.getAdsComment(anyLong(), anyLong())).thenReturn(comment);
        when(commentMapper.toDto((Comment) any())).thenReturn(new AdCommentDto(1, "Jan 1, 2020 8:00am GMT+0100", "Text"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ads/{ad_pk}/comments/{id}", 1L, 123L);
        MockMvcBuilders.standaloneSetup(adsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"pk\":1,\"author\":0,\"createdAt\":\"Jan 1, 2020 8:00am GMT+0100\",\"text\":\"Text\"}"));
    }

    /**
     * Method under test: {@link AdsController#addAds(MultipartFile, CreateAdDto)}
     */
    @Test
    void testAddAds() throws Exception {
        when(adService.getAllAds()).thenReturn(new ArrayList<>());
        when(adMapper.toDto((Collection<Ad>) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/ads");
        MockHttpServletRequestBuilder paramResult = getResult.param("adsImage", String.valueOf((Object) null));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("createAdDto", String.valueOf(new CreateAdDto()));
        MockMvcBuilders.standaloneSetup(adsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"count\":0,\"result\":[]}"));
    }

    /**
     * Method under test: {@link AdsController#deleteAdsComment(long, long)}
     */
    @Test
    void testDeleteAdsComment() throws Exception {
        Image image = new Image();
        image.setData("AAAAAAAA".getBytes("UTF-8"));
        image.setFileSize(3L);
        image.setId(123L);
        image.setMediaType("Media Type");

        User user = new User();
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setImage(image);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setPhone("4105551212");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setRegDate(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setRole(Role.USER);

        Image image1 = new Image();
        image1.setData("AAAAAAAA".getBytes("UTF-8"));
        image1.setFileSize(3L);
        image1.setId(123L);
        image1.setMediaType("Media Type");

        Ad ad = new Ad();
        ad.setAuthor(user);
        ad.setDescription("The characteristics of someone or something");
        ad.setId(123L);
        ad.setImage(image1);
        ad.setPrice(1);
        ad.setTitle("Dr");

        Image image2 = new Image();
        image2.setData("AAAAAAAA".getBytes("UTF-8"));
        image2.setFileSize(3L);
        image2.setId(123L);
        image2.setMediaType("Media Type");

        User user1 = new User();
        user1.setCity("Oxford");
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setImage(image2);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setPhone("4105551212");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setRegDate(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        user1.setRole(Role.USER);

        Comment comment = new Comment();
        comment.setAd(ad);
        comment.setAuthor(user1);
        comment.setCreatedAt("Jan 1, 2020 8:00am GMT+0100");
        comment.setId(123L);
        comment.setText("Text");
        when(adService.deleteAdsComment(anyLong(), anyLong())).thenReturn(comment);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/ads/{ad_pk}/comments/{id}", 1L,
                123L);
        MockMvcBuilders.standaloneSetup(adsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("\"OK\""));
    }

    /**
     * Method under test: {@link AdsController#updateComments(int, int, AdCommentDto)}
     */
    @Test
    void testUpdateComments() throws Exception {
        Image image = new Image();
        image.setData("AAAAAAAA".getBytes("UTF-8"));
        image.setFileSize(3L);
        image.setId(123L);
        image.setMediaType("Media Type");

        User user = new User();
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setImage(image);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setPhone("4105551212");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setRegDate(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setRole(Role.USER);

        Image image1 = new Image();
        image1.setData("AAAAAAAA".getBytes("UTF-8"));
        image1.setFileSize(3L);
        image1.setId(123L);
        image1.setMediaType("Media Type");

        Ad ad = new Ad();
        ad.setAuthor(user);
        ad.setDescription("The characteristics of someone or something");
        ad.setId(123L);
        ad.setImage(image1);
        ad.setPrice(1);
        ad.setTitle("Dr");

        Image image2 = new Image();
        image2.setData("AAAAAAAA".getBytes("UTF-8"));
        image2.setFileSize(3L);
        image2.setId(123L);
        image2.setMediaType("Media Type");

        User user1 = new User();
        user1.setCity("Oxford");
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setImage(image2);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setPhone("4105551212");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setRegDate(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        user1.setRole(Role.USER);

        Comment comment = new Comment();
        comment.setAd(ad);
        comment.setAuthor(user1);
        comment.setCreatedAt("Jan 1, 2020 8:00am GMT+0100");
        comment.setId(123L);
        comment.setText("Text");
        when(adService.updateComments(anyInt(), anyInt(), (Comment) any())).thenReturn(comment);

        Image image3 = new Image();
        image3.setData("AAAAAAAA".getBytes("UTF-8"));
        image3.setFileSize(3L);
        image3.setId(123L);
        image3.setMediaType("Media Type");

        User user2 = new User();
        user2.setCity("Oxford");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setImage(image3);
        user2.setLastName("Doe");
        user2.setPassword("iloveyou");
        user2.setPhone("4105551212");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setRegDate(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant());
        user2.setRole(Role.USER);

        Image image4 = new Image();
        image4.setData("AAAAAAAA".getBytes("UTF-8"));
        image4.setFileSize(3L);
        image4.setId(123L);
        image4.setMediaType("Media Type");

        Ad ad1 = new Ad();
        ad1.setAuthor(user2);
        ad1.setDescription("The characteristics of someone or something");
        ad1.setId(123L);
        ad1.setImage(image4);
        ad1.setPrice(1);
        ad1.setTitle("Dr");

        Image image5 = new Image();
        image5.setData("AAAAAAAA".getBytes("UTF-8"));
        image5.setFileSize(3L);
        image5.setId(123L);
        image5.setMediaType("Media Type");

        User user3 = new User();
        user3.setCity("Oxford");
        user3.setEmail("jane.doe@example.org");
        user3.setFirstName("Jane");
        user3.setId(123L);
        user3.setImage(image5);
        user3.setLastName("Doe");
        user3.setPassword("iloveyou");
        user3.setPhone("4105551212");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user3.setRegDate(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant());
        user3.setRole(Role.USER);

        Comment comment1 = new Comment();
        comment1.setAd(ad1);
        comment1.setAuthor(user3);
        comment1.setCreatedAt("Jan 1, 2020 8:00am GMT+0100");
        comment1.setId(123L);
        comment1.setText("Text");
        when(commentMapper.toEntity((AdCommentDto) any())).thenReturn(comment1);
        when(commentMapper.toDto((Comment) any())).thenReturn(new AdCommentDto(1, "Jan 1, 2020 8:00am GMT+0100", "Text"));

        AdCommentDto adCommentDto = new AdCommentDto();
        adCommentDto.setAuthor(1);
        adCommentDto.setCreatedAt("Jan 1, 2020 8:00am GMT+0100");
        adCommentDto.setPk(1);
        adCommentDto.setText("Text");
        String content = (new ObjectMapper()).writeValueAsString(adCommentDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/ads/{ad_pk}/comments/{id}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(adsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"pk\":1,\"author\":0,\"createdAt\":\"Jan 1, 2020 8:00am GMT+0100\",\"text\":\"Text\"}"));
    }

    /**
     * Method under test: {@link AdsController#updateAdsImage(int, MultipartFile)}
     */
    @Test
    void testUpdateAdsImage() throws IOException {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of AdsController.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        Image image = new Image();
        image.setData("AAAAAAAA".getBytes("UTF-8"));
        image.setFileSize(3L);
        image.setId(123L);
        image.setMediaType("Media Type");

        User user = new User();
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setImage(image);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setPhone("4105551212");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setRegDate(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setRole(Role.USER);

        Image image1 = new Image();
        image1.setData("AAAAAAAA".getBytes("UTF-8"));
        image1.setFileSize(3L);
        image1.setId(123L);
        image1.setMediaType("Media Type");

        Ad ad = new Ad();
        ad.setAuthor(user);
        ad.setDescription("The characteristics of someone or something");
        ad.setId(123L);
        ad.setImage(image1);
        ad.setPrice(1);
        ad.setTitle("Dr");
        Optional<Ad> ofResult = Optional.of(ad);

        Image image2 = new Image();
        image2.setData("AAAAAAAA".getBytes("UTF-8"));
        image2.setFileSize(3L);
        image2.setId(123L);
        image2.setMediaType("Media Type");

        User user1 = new User();
        user1.setCity("Oxford");
        user1.setEmail("jane.doe@example.org");
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setImage(image2);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setPhone("4105551212");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user1.setRegDate(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant());
        user1.setRole(Role.USER);

        Image image3 = new Image();
        image3.setData("AAAAAAAA".getBytes("UTF-8"));
        image3.setFileSize(3L);
        image3.setId(123L);
        image3.setMediaType("Media Type");

        Ad ad1 = new Ad();
        ad1.setAuthor(user1);
        ad1.setDescription("The characteristics of someone or something");
        ad1.setId(123L);
        ad1.setImage(image3);
        ad1.setPrice(1);
        ad1.setTitle("Dr");
        AdRepository adRepository = mock(AdRepository.class);
        when(adRepository.save((Ad) any())).thenReturn(ad1);
        when(adRepository.findById((Long) any())).thenReturn(ofResult);

        Image image4 = new Image();
        image4.setData("AAAAAAAA".getBytes("UTF-8"));
        image4.setFileSize(3L);
        image4.setId(123L);
        image4.setMediaType("Media Type");
        ImageRepository imageRepository = mock(ImageRepository.class);
        when(imageRepository.save((Image) any())).thenReturn(image4);
        ImageService imageService = new ImageService(imageRepository);
        CommentRepository commentRepository = mock(CommentRepository.class);
        ImageRepository imageRepository1 = mock(ImageRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        AdMapperImpl adMapper = new AdMapperImpl();
        AdService adservice = new AdService(adRepository, commentRepository, imageRepository1, userRepository,
                imageService, adMapper, new CommentMapperImpl());

        AdMapperImpl adMapper1 = new AdMapperImpl();
        CommentMapperImpl commentMapper = new CommentMapperImpl();
        AdsController adsController = new AdsController(adservice, adMapper1, commentMapper,
                new ImageService(mock(ImageRepository.class)));
        ResponseEntity<?> actualUpdateAdsImageResult = adsController.updateAdsImage(1,
                new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8"))));
        assertNull(actualUpdateAdsImageResult.getBody());
        assertEquals(HttpStatus.OK, actualUpdateAdsImageResult.getStatusCode());
        assertTrue(actualUpdateAdsImageResult.getHeaders().isEmpty());
        verify(adRepository).save((Ad) any());
        verify(adRepository).findById((Long) any());
        verify(imageRepository).save((Image) any());
    }

    /**
     * Method under test: {@link AdsController#getAdsImage(int, MultipartFile)}
     */
    @Test
    void testGetAdsImage() throws IOException {
        Image image = new Image();
        image.setData("AAAAAAAA".getBytes("UTF-8"));
        image.setFileSize(3L);
        image.setId(123L);
        image.setMediaType("Media Type");
        ImageRepository imageRepository = mock(ImageRepository.class);
        when(imageRepository.findById((Long) any())).thenReturn(Optional.of(image));
        ImageService imageService = new ImageService(imageRepository);
        AdRepository adRepository = mock(AdRepository.class);
        CommentRepository commentRepository = mock(CommentRepository.class);
        ImageRepository imageRepository1 = mock(ImageRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        ImageService imageService1 = new ImageService(mock(ImageRepository.class));
        AdMapperImpl adMapper = new AdMapperImpl();
        AdService adservice = new AdService(adRepository, commentRepository, imageRepository1, userRepository,
                imageService1, adMapper, new CommentMapperImpl());

        AdMapperImpl adMapper1 = new AdMapperImpl();
        AdsController adsController = new AdsController(adservice, adMapper1, new CommentMapperImpl(), imageService);
        ResponseEntity<byte[]> actualAdsImage = adsController.getAdsImage(1,
                new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8"))));
        assertEquals(8, actualAdsImage.getBody().length);
        assertEquals(HttpStatus.OK, actualAdsImage.getStatusCode());
        assertTrue(actualAdsImage.getHeaders().isEmpty());
        verify(imageRepository).findById((Long) any());
    }


    /**
     * Method under test: {@link AdsController#addAdsComment(int, AdCommentDto)}
     */
    @Test
    void testAddAdsComment() throws Exception {
        AdCommentDto adCommentDto = new AdCommentDto();
        adCommentDto.setAuthor(1);
        adCommentDto.setCreatedAt("Jan 1, 2020 8:00am GMT+0100");
        adCommentDto.setPk(1);
        adCommentDto.setText("Text");
        String content = (new ObjectMapper()).writeValueAsString(adCommentDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/ads/{ad_pk}/comments", "Uri Vars", "Uri Vars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adsController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link AdsController#getAdsMe()}
     */
    @Test
    void testGetAdsMe() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders
                .formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adsController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link AdsController#removeAds(int)}
     */
    @Test
    void testRemoveAds() throws Exception {
        Image image = new Image();
        image.setData("AAAAAAAA".getBytes("UTF-8"));
        image.setFileSize(3L);
        image.setId(123L);
        image.setMediaType("Media Type");

        User user = new User();
        user.setCity("Oxford");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setImage(image);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setPhone("4105551212");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        user.setRegDate(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());
        user.setRole(Role.USER);

        Image image1 = new Image();
        image1.setData("AAAAAAAA".getBytes("UTF-8"));
        image1.setFileSize(3L);
        image1.setId(123L);
        image1.setMediaType("Media Type");

        Ad ad = new Ad();
        ad.setAuthor(user);
        ad.setDescription("The characteristics of someone or something");
        ad.setId(123L);
        ad.setImage(image1);
        ad.setPrice(1);
        ad.setTitle("Dr");
        when(adService.removeAdsById(anyInt())).thenReturn(ad);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/ads/{id}", 1);
        MockMvcBuilders.standaloneSetup(adsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

