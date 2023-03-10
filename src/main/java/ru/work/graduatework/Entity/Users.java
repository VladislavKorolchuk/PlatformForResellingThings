package ru.work.graduatework.Entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * Entity Users
 */
@Entity  // A special class whose objects are saved to the database
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // delegates the installation of the ID to the database level
    @Column(name = "primary_key")
    private Integer id;                                 // Primary key
    @Column(name = "first_name")
    private String firstName;                           // User's name
    @Column(name = "last_name")
    private String lastName;                            // User's last name
    @Column(name = "phone_number")
    private String phone;                               // User's phone number
    @Column(name = "email")
    private String email;                               // User's email address
    @Column(name = "reg_date")
    private String regDate;                             // Registration date
    @Column(name = "city")
    private String city;                                // The user's location city
    @Column(name = "image")
    private String image;                               // User image

    @OneToMany(mappedBy = "user")                       // type of database connection
    Collection<Ads> adsCollection;

    @OneToMany(mappedBy = "user")                       // type of database connection
    // @JoinColumn (name = "ads_id")
    Collection<Comment> commentCollection;

    // ----------------- block Getter's and Setter's ---------------------
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
        return adsCollection;
    }

    public void setAds(Collection<Ads> ads) {
        this.adsCollection = ads;
    }

    public Collection<Ads> getAdsCollection() {
        return adsCollection;
    }

    public void setAdsCollection(Collection<Ads> adsCollection) {
        this.adsCollection = adsCollection;
    }

    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
    }
}
