package ru.work.graduatework.Entity;

import javax.persistence.*;
import java.util.Collection;

/** Entity Users
 *
 */
@Entity  // A special class whose objects are saved to the database
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // delegates the installation of the ID to the database level
    private Integer id;                                 // Primary key

    private String firstName;                           // User's name

    private String lastName;                            // User's last name

    private String phone;                               // User's phone number

    private String email;                               // User's email address

    private String regDate;                             // Registration date

    private String city;                                // The user's location city

    private String image;                               // User image

    @OneToMany(mappedBy = "user")                       // type of database connection
//    @JoinColumn(name = "ads_id")
    Collection<Ads> ads;

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
        return ads;
    }

    public void setAds(Collection<Ads> ads) {
        this.ads = ads;
    }

}
