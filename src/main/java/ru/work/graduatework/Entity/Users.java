package ru.work.graduatework.Entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;                      // Идентификатор

    @Column(name = "user_first_name")
    private String firstName;             // Имя пользователя

    @Column(name = "user_last_name")
    private String lastName;              //Фамилия пользователя

    @Column(name = "user_phone_number")
    private String phone;                 // Телефон пользователя

    @Column(name = "user_ email")         // e-mail пользователя
    private String email;

    @Column(name = "user_regDate")        // Дата регистрации пользователя
    private String regDate;

    @Column(name = "user_city")           // Город пользователя
    private String city;

    @Column(name = "user_image")          // Картинка пользователя
    private String image;

    @OneToMany(mappedBy = "user")
//    @JoinColumn(name = "ads_id")
    Collection<Ads> ads;


}
