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
    @Column(name = "primary_key")
    private int pk;
//    @Column(name = "Comment_author") //user id
//    private int author;
    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)                  // Many-to-one communication
    @JoinColumn(name = "Ads_user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ads")
    private Ads ads;


}
