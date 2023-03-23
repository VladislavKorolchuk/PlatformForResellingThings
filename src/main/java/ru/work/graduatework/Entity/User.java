package ru.work.graduatework.Entity;

import java.time.Instant;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.work.graduatework.dto.Role;

@Entity  // A special class whose objects are saved to the database
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "first_name")
    private String firstName;
    // User's name
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;                               // User's email address

    @Column(name = "password_authorizations")
    private String password;

    @Column(name = "phone")
    private String phone;
    @Column(name = "city")
    private String city;
    @Column(name = "reg_date")
    private Instant regDate;                             // Registration date

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;
    @Column(name = "role_authorizations")
    @Enumerated(EnumType.STRING)
    private Role role;

}
