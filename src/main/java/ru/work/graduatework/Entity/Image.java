package ru.work.graduatework.Entity;

import javax.persistence.*;

@Entity
@Table(name = "Image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Image_id")
    private Long id;                        // Идентификатор

    @Column(name = "Image_idAds")
    private Long idAds;                     // Идентификатор объявления

    @Column(name = "Image_image")
    private String image ;                  // Изображение

    @OneToOne                               // Связь один к одному
    private Ads ads;

}
