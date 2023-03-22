package ru.work.graduatework.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Getter
@Setter
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private int price;                      // Price

    @ManyToOne(fetch = FetchType.EAGER)          // Many-to-one communication
    @JoinColumn(name = "author_id")
    private Users author;

    @OneToOne()
    @JoinColumn()
    private Image image;

}
