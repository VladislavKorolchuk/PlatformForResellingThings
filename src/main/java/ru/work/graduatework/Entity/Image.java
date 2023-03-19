package ru.work.graduatework.Entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;                        // ID

//    @Column(name = "Image_idAds")
//    private Long idAds;                     // ID ads
//
//    @Column(name = "Image_image")
//    private String image ;                  // Image

    private String filePath;
    private Long fileSize;
    private String mediaType;

    @Lob
    @Type(type = "binary")
    private byte[] data;

//    @OneToOne(fetch = FetchType.LAZY)
////    @JoinColumn(name = "ads_id")
//    private Ads ads;

//    @OneToOne
////    @JoinColumn(name = "user_id")
//    private Users users;

    public String toString() {
        return "Ads(id=" + this.getId() + ", image=" + Arrays.toString((this.getData())) + ")";
    }

}
