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
    @Column
    private long id;
//    @Column(name = "Comment_author") //user id
//    private int author;
    @Column
    private String createdAt;

    @Column
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)                  // Many-to-one communication
    @JoinColumn
    private Users author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ads")
    private Ads ad;


}
