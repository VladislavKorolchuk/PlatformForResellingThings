package ru.work.graduatework.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Ads")
@Getter
@Setter
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "primary_key")
    private Integer pk;                         // User ID
    @Column(name = "Ads_author")
    private Integer author;                     // ID

    @Column(name = "Ads_image")
    private String image;

    @Column(name = "Ads_price")
    private Integer price;                      // Price

    @Column(name = "Ads_title")
    private String title;                       // Title

    @ManyToOne(fetch = FetchType.LAZY)          // Many-to-one communication
    @JoinColumn(name = "Ads_user_id")
    private Users user;

    @OneToMany(mappedBy = "ads")                 // Many images to one ads
    private List<Image> images;

}
