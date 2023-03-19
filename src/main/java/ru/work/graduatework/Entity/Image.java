package ru.work.graduatework.Entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "Image")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Image_id")
    private long id;                        // ID

    private Long fileSize;

    private String filePath;
    private String mediaType;

    @Lob
    @Type(type = "binary")
    private byte[] data;

    //    @Column(name = "Image_idAds")
//    private Long idAds;                     // ID ads
//
//    @Column(name = "Image_image")
//    private String image ;                  // Image
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
