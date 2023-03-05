package ru.work.graduatework.Entity;

import javax.persistence.*;

@Entity
@Table(name = "Ads")
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author")
    private Integer author;                     // Идентификатор

//    private List<String> image;   не работает

    @Column(name = "Ads_pk")
    private Integer pk;                        // Идентификатор пользователя ???

    @Column(name = "Ads_description")       // Описание
    private float description;

    @Column(name = "Ads_price")
    private Integer price;                    // Цена

    @Column(name = "Ads_title")             // Заглавие
    private String title;

    @Column(name = "Ads_createdAt")         // Дата создания  ???
    private String createdAt;

    @ManyToOne
    @JoinColumn(name = "Ads_user_id")
    private Users user;

}
