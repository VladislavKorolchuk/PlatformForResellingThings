package ru.work.graduatework.Entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
@Table(name="image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;                        // ID

    @Column(name = "file_size")
    private Long fileSize;

    //private String filePath;
    @Column(name = "media_type")
    private String mediaType;

    @Column(name = "data_image")
    @Lob
    @Type(type = "binary")
    private byte[] data;

    public String toString() {
        return "Ad(id=" + this.getId() + ", image=" + Arrays.toString((this.getData())) + ")";
    }

}
