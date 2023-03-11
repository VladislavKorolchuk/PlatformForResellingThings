package ru.work.graduatework.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "primary_key")
    private int pk;
    @Column(name = "Comment_author")
    private int author;
    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "text")
    private String text;

    @ManyToOne                                  // Many-to-one communication
    @JoinColumn(name = "Ads_user_id")
    private Users user;

}
