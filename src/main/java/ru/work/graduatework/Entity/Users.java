package ru.work.graduatework.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.work.graduatework.dto.Role;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entity Users
 */
@Entity  // A special class whose objects are saved to the database
@Getter
@Setter
public class Users implements UserDetails {

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

    @Column(name = "currPass")
    private String currentPassword;
    @Column(name = "newPass",nullable = true)
    private String newPassword;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")                       // type of database connection
    Collection<Ads> adsCollection;

    @OneToMany(mappedBy = "user")                       // type of database connection
    Collection<Comment> commentCollection;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

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

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(this.role.name()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
