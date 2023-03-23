package ru.work.graduatework.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Ads")
@Getter
@Setter
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "primary_key")
    private long id;
    private String title;
    private String description;
    private int price;                      // Price

    @ManyToOne(fetch = FetchType.LAZY)          // Many-to-one communication
    @JoinColumn(name = "author_id")
    private Users author;

    @OneToOne()
    @JoinColumn()
    private Image image;

}
