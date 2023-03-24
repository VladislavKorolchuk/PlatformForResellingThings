package ru.work.graduatework.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.work.graduatework.Entity.Ad;
import ru.work.graduatework.Entity.Comment;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.Entity.User;
import ru.work.graduatework.dto.AdCommentDto;
import ru.work.graduatework.dto.CreateAdDto;
import ru.work.graduatework.dto.FullAdDto;
import ru.work.graduatework.dto.Role;
import ru.work.graduatework.mapper.AdMapper;
import ru.work.graduatework.mapper.CommentMapper;
import ru.work.graduatework.repository.AdRepository;
import ru.work.graduatework.repository.CommentRepository;
import ru.work.graduatework.repository.ImageRepository;
import ru.work.graduatework.repository.UserRepository;

@ContextConfiguration(classes = {AdService.class})
@ExtendWith(SpringExtension.class)
class AdServiceTest {
    @MockBean
    private AdMapper adMapper;

    @MockBean
    private AdRepository adRepository;

    @Autowired
    private AdService adService;

    @MockBean
    private CommentMapper commentMapper;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private ImageRepository imageRepository;

    @MockBean
    private ImageService imageService;

    @MockBean
    private UserRepository userRepository;


    @Test
    void testGetAllAds() {
        ArrayList<Ad> adList = new ArrayList<>();
        when(adRepository.findAll()).thenReturn(adList);
        Collection<Ad> actualAllAds = adService.getAllAds();
        assertSame(adList, actualAllAds);
        assertTrue(actualAllAds.isEmpty());
        verify(adRepository).findAll();
    }


    @Test
    void testGetAllAds2() {
        when(adRepository.findAll()).thenThrow(new ResponseStatusException(HttpStatus.CONTINUE));
        assertThrows(ResponseStatusException.class, () -> adService.getAllAds());
        verify(adRepository).findAll();
    }

    @Test
    void testAddAds() throws IOException {
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
        when(adRepository.save((Ad) any())).thenReturn(ad);

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
        Optional<User> ofResult = Optional.of(user1);
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);

        Image image3 = new Image();
        image3.setData("AAAAAAAA".getBytes("UTF-8"));
        image3.setFileSize(3L);
        image3.setId(123L);
        image3.setMediaType("Media Type");
        when(imageService.uploadImage((MultipartFile) any())).thenReturn(image3);

        Image image4 = new Image();
        image4.setData("AAAAAAAA".getBytes("UTF-8"));
        image4.setFileSize(3L);
        image4.setId(123L);
        image4.setMediaType("Media Type");

        User user2 = new User();
        user2.setCity("Oxford");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setImage(image4);
        user2.setLastName("Doe");
        user2.setPassword("iloveyou");
        user2.setPhone("4105551212");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setRegDate(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant());
        user2.setRole(Role.USER);

        Image image5 = new Image();
        image5.setData("AAAAAAAA".getBytes("UTF-8"));
        image5.setFileSize(3L);
        image5.setId(123L);
        image5.setMediaType("Media Type");

        Ad ad1 = new Ad();
        ad1.setAuthor(user2);
        ad1.setDescription("The characteristics of someone or something");
        ad1.setId(123L);
        ad1.setImage(image5);
        ad1.setPrice(1);
        ad1.setTitle("Dr");
        when(adMapper.toEntity((CreateAdDto) any())).thenReturn(ad1);

        CreateAdDto createAdDto = new CreateAdDto();
        createAdDto.setDescription("The characteristics of someone or something");
        createAdDto.setPrice(1);
        createAdDto.setTitle("Dr");
        assertSame(ad, adService.addAds(createAdDto,
                new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8"))), "jane.doe@example.org"));
        verify(adRepository).save((Ad) any());
        verify(userRepository).findByEmail((String) any());
        verify(imageService).uploadImage((MultipartFile) any());
        verify(adMapper).toEntity((CreateAdDto) any());
    }

    @Test
    void testGetFullAd() throws Exception {
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
        when(adRepository.findById((Long) any())).thenReturn(ofResult);

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
        when(adMapper.toFullAdsDto((Ad) any())).thenReturn(fullAdDto);
        assertSame(fullAdDto, adService.getFullAd(123L));
        verify(adRepository).findById((Long) any());
        verify(adMapper).toFullAdsDto((Ad) any());
    }

    @Test
    void testGetFullAd2() throws Exception {
        when(adRepository.findById((Long) any())).thenReturn(Optional.empty());

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
        when(adMapper.toFullAdsDto((Ad) any())).thenReturn(fullAdDto);
        assertThrows(Exception.class, () -> adService.getFullAd(123L));
        verify(adRepository).findById((Long) any());
    }

    @Test
    void testRemoveAds() throws UnsupportedEncodingException {
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
        doNothing().when(adRepository).delete((Ad) any());
        when(adRepository.findById((Long) any())).thenReturn(ofResult);
        doNothing().when(imageRepository).delete((Image) any());
        adService.removeAds(123L);
        verify(adRepository).findById((Long) any());
        verify(adRepository).delete((Ad) any());
        verify(imageRepository).delete((Image) any());
    }

    @Test
    void testUpdateAds() throws UnsupportedEncodingException {
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
        when(adRepository.save((Ad) any())).thenReturn(ad1);
        when(adRepository.findById((Long) any())).thenReturn(ofResult);

        CreateAdDto createAdDto = new CreateAdDto();
        createAdDto.setDescription("The characteristics of someone or something");
        createAdDto.setPrice(1);
        createAdDto.setTitle("Dr");
        assertSame(ad1, adService.updateAds(123, createAdDto));
        verify(adRepository).save((Ad) any());
        verify(adRepository).findById((Long) any());
    }

    @Test
    void testGetAdsMe() throws UnsupportedEncodingException {
        ArrayList<Ad> adList = new ArrayList<>();
        when(adRepository.findAllByAuthorId(anyLong())).thenReturn(adList);

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
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult);
        Collection<Ad> actualAdsMe = adService.getAdsMe("jane.doe@example.org");
        assertSame(adList, actualAdsMe);
        assertTrue(actualAdsMe.isEmpty());
        verify(adRepository).findAllByAuthorId(anyLong());
        verify(userRepository).findByEmail((String) any());
    }
    @Test
    void testGetComments() {
        ArrayList<Comment> commentList = new ArrayList<>();
        when(commentRepository.findAllByAdId(anyLong())).thenReturn(commentList);
        Collection<Comment> actualComments = adService.getComments(1L);
        assertSame(commentList, actualComments);
        assertTrue(actualComments.isEmpty());
        verify(commentRepository).findAllByAdId(anyLong());
    }

    @Test
    void testGetComments2() {
        when(commentRepository.findAllByAdId(anyLong())).thenThrow(new ResponseStatusException(HttpStatus.CONTINUE));
        assertThrows(ResponseStatusException.class, () -> adService.getComments(1L));
        verify(commentRepository).findAllByAdId(anyLong());
    }

    @Test
    void testGetCommentsId() throws UnsupportedEncodingException {
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
        when(adRepository.findById((Long) any())).thenReturn(ofResult);
        AdCommentDto actualCommentsId = adService.getCommentsId(1L, 1);
        assertNull(actualCommentsId.getText());
        assertEquals(0, actualCommentsId.getPk());
        assertNull(actualCommentsId.getCreatedAt());
        verify(adRepository).findById((Long) any());
    }

    @Test
    void testUpdateComments() throws UnsupportedEncodingException {
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
        Optional<Comment> ofResult = Optional.of(comment);

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
        when(commentRepository.save((Comment) any())).thenReturn(comment1);
        when(commentRepository.findByIdAndAdId(anyLong(), anyLong())).thenReturn(ofResult);

        Image image6 = new Image();
        image6.setData("AAAAAAAA".getBytes("UTF-8"));
        image6.setFileSize(3L);
        image6.setId(123L);
        image6.setMediaType("Media Type");

        User user4 = new User();
        user4.setCity("Oxford");
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setId(123L);
        user4.setImage(image6);
        user4.setLastName("Doe");
        user4.setPassword("iloveyou");
        user4.setPhone("4105551212");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user4.setRegDate(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant());
        user4.setRole(Role.USER);

        Image image7 = new Image();
        image7.setData("AAAAAAAA".getBytes("UTF-8"));
        image7.setFileSize(3L);
        image7.setId(123L);
        image7.setMediaType("Media Type");

        Ad ad2 = new Ad();
        ad2.setAuthor(user4);
        ad2.setDescription("The characteristics of someone or something");
        ad2.setId(123L);
        ad2.setImage(image7);
        ad2.setPrice(1);
        ad2.setTitle("Dr");

        Image image8 = new Image();
        image8.setData("AAAAAAAA".getBytes("UTF-8"));
        image8.setFileSize(3L);
        image8.setId(123L);
        image8.setMediaType("Media Type");

        User user5 = new User();
        user5.setCity("Oxford");
        user5.setEmail("jane.doe@example.org");
        user5.setFirstName("Jane");
        user5.setId(123L);
        user5.setImage(image8);
        user5.setLastName("Doe");
        user5.setPassword("iloveyou");
        user5.setPhone("4105551212");
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user5.setRegDate(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant());
        user5.setRole(Role.USER);

        Comment comment2 = new Comment();
        comment2.setAd(ad2);
        comment2.setAuthor(user5);
        comment2.setCreatedAt("Jan 1, 2020 8:00am GMT+0100");
        comment2.setId(123L);
        comment2.setText("Text");
        assertSame(comment1, adService.updateComments(1, 1, comment2));
        verify(commentRepository).save((Comment) any());
        verify(commentRepository).findByIdAndAdId(anyLong(), anyLong());
    }

    @Test
    void testGetAdsById() throws UnsupportedEncodingException {
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
        when(adRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(ad, adService.getAdsById(123L));
        verify(adRepository).findById((Long) any());
    }

    @Test
    void testGetAdsById2() {
        when(adRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> adService.getAdsById(123L));
        verify(adRepository).findById((Long) any());
    }

    @Test
    void testGetAdsComment() throws UnsupportedEncodingException {
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
        Optional<Comment> ofResult = Optional.of(comment);
        when(commentRepository.findByIdAndAdId(anyLong(), anyLong())).thenReturn(ofResult);
        assertSame(comment, adService.getAdsComment(1L, 123L));
        verify(commentRepository).findByIdAndAdId(anyLong(), anyLong());
    }

    @Test
    void testDeleteAdsComment() throws UnsupportedEncodingException {
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
        Optional<Comment> ofResult = Optional.of(comment);
        doNothing().when(commentRepository).delete((Comment) any());
        when(commentRepository.findByIdAndAdId(anyLong(), anyLong())).thenReturn(ofResult);
        assertSame(comment, adService.deleteAdsComment(1L, 123L));
        verify(commentRepository).findByIdAndAdId(anyLong(), anyLong());
        verify(commentRepository).delete((Comment) any());
    }


    @Test
    void testUpdateAdsImage() throws IOException {
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
        when(adRepository.save((Ad) any())).thenReturn(ad1);
        when(adRepository.findById((Long) any())).thenReturn(ofResult);

        Image image4 = new Image();
        image4.setData("AAAAAAAA".getBytes("UTF-8"));
        image4.setFileSize(3L);
        image4.setId(123L);
        image4.setMediaType("Media Type");
        when(imageService.uploadImage((MultipartFile) any())).thenReturn(image4);
        adService.updateAdsImage(1,
                new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8"))));
        verify(adRepository).save((Ad) any());
        verify(adRepository).findById((Long) any());
        verify(imageService).uploadImage((MultipartFile) any());
    }

    @Test
    void testRemoveAdsById() throws UnsupportedEncodingException {
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
        doNothing().when(adRepository).delete((Ad) any());
        when(adRepository.findById((Long) any())).thenReturn(ofResult);
        doNothing().when(commentRepository).deleteAdsCommentsByAdId(anyLong());
        assertSame(ad, adService.removeAdsById(123));
        verify(adRepository).findById((Long) any());
        verify(adRepository).delete((Ad) any());
        verify(commentRepository).deleteAdsCommentsByAdId(anyLong());
    }

    @Test
    void testAddAdsComment() throws Exception {
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
        when(adRepository.findById((Long) any())).thenReturn(ofResult);

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

        Image image4 = new Image();
        image4.setData("AAAAAAAA".getBytes("UTF-8"));
        image4.setFileSize(3L);
        image4.setId(123L);
        image4.setMediaType("Media Type");

        User user2 = new User();
        user2.setCity("Oxford");
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setImage(image4);
        user2.setLastName("Doe");
        user2.setPassword("iloveyou");
        user2.setPhone("4105551212");
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user2.setRegDate(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant());
        user2.setRole(Role.USER);

        Comment comment = new Comment();
        comment.setAd(ad1);
        comment.setAuthor(user2);
        comment.setCreatedAt("Jan 1, 2020 8:00am GMT+0100");
        comment.setId(123L);
        comment.setText("Text");
        when(commentRepository.save((Comment) any())).thenReturn(comment);

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
        Optional<User> ofResult1 = Optional.of(user3);
        when(userRepository.findByEmail((String) any())).thenReturn(ofResult1);

        Image image6 = new Image();
        image6.setData("AAAAAAAA".getBytes("UTF-8"));
        image6.setFileSize(3L);
        image6.setId(123L);
        image6.setMediaType("Media Type");

        User user4 = new User();
        user4.setCity("Oxford");
        user4.setEmail("jane.doe@example.org");
        user4.setFirstName("Jane");
        user4.setId(123L);
        user4.setImage(image6);
        user4.setLastName("Doe");
        user4.setPassword("iloveyou");
        user4.setPhone("4105551212");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user4.setRegDate(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant());
        user4.setRole(Role.USER);

        Image image7 = new Image();
        image7.setData("AAAAAAAA".getBytes("UTF-8"));
        image7.setFileSize(3L);
        image7.setId(123L);
        image7.setMediaType("Media Type");

        Ad ad2 = new Ad();
        ad2.setAuthor(user4);
        ad2.setDescription("The characteristics of someone or something");
        ad2.setId(123L);
        ad2.setImage(image7);
        ad2.setPrice(1);
        ad2.setTitle("Dr");

        Image image8 = new Image();
        image8.setData("AAAAAAAA".getBytes("UTF-8"));
        image8.setFileSize(3L);
        image8.setId(123L);
        image8.setMediaType("Media Type");

        User user5 = new User();
        user5.setCity("Oxford");
        user5.setEmail("jane.doe@example.org");
        user5.setFirstName("Jane");
        user5.setId(123L);
        user5.setImage(image8);
        user5.setLastName("Doe");
        user5.setPassword("iloveyou");
        user5.setPhone("4105551212");
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        user5.setRegDate(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant());
        user5.setRole(Role.USER);

        Comment comment1 = new Comment();
        comment1.setAd(ad2);
        comment1.setAuthor(user5);
        comment1.setCreatedAt("Jan 1, 2020 8:00am GMT+0100");
        comment1.setId(123L);
        comment1.setText("Text");
        when(commentMapper.toEntity((AdCommentDto) any())).thenReturn(comment1);
        assertSame(comment, adService.addAdsComment(2, new AdCommentDto(1, "Jan 1, 2020 8:00am GMT+0100", "Text"),
                "jane.doe@example.org"));
        verify(adRepository).findById((Long) any());
        verify(commentRepository).save((Comment) any());
        verify(userRepository).findByEmail((String) any());
        verify(commentMapper).toEntity((AdCommentDto) any());
    }

}

