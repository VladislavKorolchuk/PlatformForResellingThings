package ru.work.graduatework.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
@Table(name="ad")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private int price;                      // Price

    @ManyToOne(fetch = FetchType.EAGER)          // Many-to-one communication
    @JoinColumn(name = "author_id")
    private User author;

    @OneToOne()
    @JoinColumn(name ="image_id")
    private Image image;

}
