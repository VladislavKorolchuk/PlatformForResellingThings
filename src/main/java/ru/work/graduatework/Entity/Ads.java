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
