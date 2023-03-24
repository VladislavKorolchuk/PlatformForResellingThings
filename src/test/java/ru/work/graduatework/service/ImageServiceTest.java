package ru.work.graduatework.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import ru.work.graduatework.Entity.Image;
import ru.work.graduatework.repository.ImageRepository;

@ContextConfiguration(classes = {ImageService.class})
@ExtendWith(SpringExtension.class)
class ImageServiceTest {
    @MockBean
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;


    @Test
    void testUploadImage() throws IOException {
        Image image = new Image();
        image.setData("AAAAAAAA".getBytes("UTF-8"));
        image.setFileSize(3L);
        image.setId(123L);
        image.setMediaType("Media Type");
        when(imageRepository.save((Image) any())).thenReturn(image);
        assertSame(image, imageService
                .uploadImage(new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")))));
        verify(imageRepository).save((Image) any());
    }


    @Test
    void testUploadImage2() throws IOException {
        when(imageRepository.save((Image) any())).thenThrow(new ResponseStatusException(HttpStatus.CONTINUE));
        assertThrows(ResponseStatusException.class, () -> imageService
                .uploadImage(new MockMultipartFile("Name", new ByteArrayInputStream("AAAAAAAA".getBytes("UTF-8")))));
        verify(imageRepository).save((Image) any());
    }

    @Test
    void testGetImageById() throws UnsupportedEncodingException {
        Image image = new Image();
        image.setData("AAAAAAAA".getBytes("UTF-8"));
        image.setFileSize(3L);
        image.setId(123L);
        image.setMediaType("Media Type");
        Optional<Image> ofResult = Optional.of(image);
        when(imageRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(image, imageService.getImageById(123L));
        verify(imageRepository).findById((Long) any());
    }

    @Test
    void testGetImageById2() {
        when(imageRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> imageService.getImageById(123L));
        verify(imageRepository).findById((Long) any());
    }
}

