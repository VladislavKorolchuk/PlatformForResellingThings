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
    private Long id;                        // ID

    @Column(name = "Image_idAds")
    private Long idAds;                     // ID ads

    @Column(name = "Image_image")
    private String image ;                  // Image

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ads_id")            // One-to-one communication
    private Ads ads;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users users;

}
