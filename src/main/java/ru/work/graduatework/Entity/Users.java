package ru.work.graduatework.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
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
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // delegates the installation of the ID to the database level
    private long id;                                 // Primary key
    private String firstName;                           // User's name
    private String lastName;                            // User's last name
    private String phone;                               // User's phone number
    private String email;                               // User's email address
    private String regDate;                             // Registration date
    private String city;                                // The user's location city
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

//    @OneToMany(mappedBy = "user")                       // type of database connection
//    Collection<Ads> adsCollection;
//
//    @OneToMany(mappedBy = "user")                       // type of database connection
//    Collection<Comment> commentCollection;

    @OneToOne
//    @JoinColumn(name = "image_id")
    private Image image;

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
