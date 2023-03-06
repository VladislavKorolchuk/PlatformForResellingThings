package ru.work.graduatework.Entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;                      // Идентификатор

    private String firstName;             // Имя пользователя

    private String lastName;              //Фамилия пользователя

    private String phone;                 // Телефон пользователя

    private String email;

    private String regDate;

    private String city;

    private String image;

    @OneToMany(mappedBy = "user")
//    @JoinColumn(name = "ads_id")
    Collection<Ads> ads;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Collection<Ads> getAds() {
        return ads;
    }

    public void setAds(Collection<Ads> ads) {
        this.ads = ads;
    }
}
