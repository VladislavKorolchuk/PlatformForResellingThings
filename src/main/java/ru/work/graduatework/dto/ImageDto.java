package ru.work.graduatework.dto;

import lombok.Data;
import ru.work.graduatework.Entity.Ads;

@Data
public class ImageDto {

    private Long id;
    private String filePath;
    private Long fileSize;
    private String mediaType;
    private byte[] data;

    public ImageDto(Long id, String filePath, Long fileSize, String mediaType, byte[] data) {
        this.id = id;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.mediaType = mediaType;
        this.data = data;
    }
}
