package ru.work.graduatework.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Ads")
@Getter
@Setter
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "primary_key")
    private Integer pk;                         // User ID
//    @Column(name = "Ads_author")
//    private Integer author;                     // ID

//    @Column(name = "Ads_image")
//    private String image;

    @Column(name = "Ads_price")
    private Integer price;                      // Price

    @JsonIgnore
    @Column(name = "Ads_description")
    private String description;

    @Column(name = "Ads_title")
    private String title;                       // Title

    @ManyToOne(fetch = FetchType.LAZY)          // Many-to-one communication
    @JoinColumn(name = "Ads_user_id")
    private Users user;

    @OneToOne()
//    @JsonIgnore  // Many images to one ads
    @JoinColumn()
    private Image image;


//    @OneToMany(mappedBy = "ads")                       // type of database connection
//    Collection<Comment> commentCollection;
}
