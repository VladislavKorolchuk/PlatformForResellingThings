package ru.work.graduatework.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

      @Column(name = "created_ad")
    private String createdAt;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)                  // Many-to-one communication
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pk_ads")
    private Ad ad;


}
