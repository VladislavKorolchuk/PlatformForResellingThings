package ru.work.graduatework.Entity;

import javax.persistence.*;

@Entity
@Table(name = "Image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Image_id")
    private Long id;                        // ID

    @Column(name = "Image_idAds")
    private Long idAds;                     // ID ads

    @Column(name = "Image_image")
    private String image ;                  // Image

    @OneToOne                               // One-to-one communication
    private Ads ads;

    // ----------------- block Getter's and Setter's ---------------------
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAds() {
        return idAds;
    }

    public void setIdAds(Long idAds) {
        this.idAds = idAds;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Ads getAds() {
        return ads;
    }

    public void setAds(Ads ads) {
        this.ads = ads;
    }

}
