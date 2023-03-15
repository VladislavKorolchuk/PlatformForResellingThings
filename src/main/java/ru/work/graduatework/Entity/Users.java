package ru.work.graduatework.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * Entity Users
 */
@Entity  // A special class whose objects are saved to the database
@Getter
@Setter
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
//    @Column(name = "image")
//    private String image;                               // User image
    @Column(name = "currPass")
    private String currentPassword;
    @Column(name = "newPass",nullable = true)
    private String newPassword;

    @OneToMany(mappedBy = "user")                       // type of database connection
    Collection<Ads> adsCollection;

    @OneToMany(mappedBy = "user")                       // type of database connection
    Collection<Comment> commentCollection;

    @OneToOne
    @JsonIgnore
    Image image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(id, users.id) && Objects.equals(phone, users.phone) && Objects.equals(email, users.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phone, email);
    }
}
