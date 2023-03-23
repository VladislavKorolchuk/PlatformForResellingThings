package ru.work.graduatework.Entity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import ru.work.graduatework.dto.Role;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Entity Users
 */
@Entity  // A special class whose objects are saved to the database
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class Users {//implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  // delegates the installation of the ID to the database level
  //   @Column(name = "primary_key")
  private long id;                                 // Primary key
  //  @Column(name = "first_name")
  private String firstName;                           // User's name
  //  @Column(name = "last_name")
  private String lastName;
  //  @Column(name = "email")
  private String email;                               // User's email address
  //  @Column(name = "phone_number")

  @Column(name = "password")
  private String password;

  private String phone;
  //  @Column(name = "city")
  private String city;
  //  @Column(name = "reg_date")
  private Instant regDate;                             // Registration date
  // The user's location city

  //  @Column(name = "currPass")

  //  @Column(name = "newPass", nullable = true)
  // private String newPassword;


  @OneToOne
//    @JoinColumn(name = "image_id")
  private Image image;
  // @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private Role role;

//    @OneToMany(mappedBy = "user")                       // type of database connection
//    Collection<Ads> adsCollection;
//
//    @OneToMany(mappedBy = "user")                       // type of database connection
//    Collection<Comment> commentCollection;

//    @Override
//    public Set<GrantedAuthority> getAuthorities() {
//        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
//        authorities.add(new SimpleGrantedAuthority(this.role.name()));
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }
}
