package ru.work.graduatework.Entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Ads")
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Ads_id")
    private Long AdsId;                     // Идентификатор

    @Column(name = "Ads_pk")
    private Long pk;                        // Идентификатор пользователя ???

    @Column(name = "Ads_description")       // Описание
    private float description;

    @Column(name = "Ads_price")
    private float price;                    // Цена

    @Column(name = "Ads_title")             // Заглавие
    private String title;

    @Column(name = "Ads_createdAt")         // Дата создания  ???
    private String createdAt;

    @ManyToOne
    @JoinColumn(name = "Ads_user_id")
    private Users user;




}
