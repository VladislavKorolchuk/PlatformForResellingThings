package ru.work.graduatework.Entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Table(name="comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

      @Column(name = "created_at")
    private String createdAt;
    @Column(name = "text_comment")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)                  // Many-to-one communication
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_id")
    private Ad ad;


}
