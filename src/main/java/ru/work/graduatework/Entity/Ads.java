package ru.work.graduatework.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Ads")
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Ads_author")
    private Integer author;                     // Идентификатор

    @Transient
    private List<String> image;

    @Column(name = "Ads_pk")
    private Integer pk;                        // Идентификатор пользователя ???

    @Column(name = "Ads_price")
    private Integer price;                    // Цена

    @Column(name = "Ads_title")             // Заглавие
    private String title;

    @ManyToOne
    @JoinColumn(name = "Ads_user_id")
    private Users user;

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

//    public List<String> getImage() {
//        return image;
//    }
//
//    public void setImage(List<String> image) {
//        this.image = image;
//    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
