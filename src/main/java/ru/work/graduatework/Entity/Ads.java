package ru.work.graduatework.Entity;

import javax.persistence.*;
@Entity
@Table(name = "Ads")
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "primary_key")
    private Integer pk;                         // User ID
    @Column(name = "Ads_author")
    private Integer author;                     // ID
//    @ElementCollection                          // Image
//    private List<String> image;
    @Column(name = "Ads_image")
    private String image;

    @Column(name = "Ads_price")
    private Integer price;                      // Price

    @Column(name = "Ads_title")
    private String title;                       // Title

    @ManyToOne                                  // Many-to-one communication
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


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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
