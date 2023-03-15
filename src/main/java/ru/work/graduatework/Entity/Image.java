package ru.work.graduatework.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Image")
@Getter
@Setter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Image_id")
    private int id;                        // ID

//    @Column(name = "Image_idAds")
//    private Long idAds;                     // ID ads
//
//    @Column(name = "Image_image")
//    private String image ;                  // Image

    private String filePath;
    private Long fileSize;
    private String mediaType;

    @Lob
    private byte[] data;

    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ads_id")
    private Ads ads;

    @OneToOne
//    @JoinColumn(name = "user_id")
    private Users users;

}
