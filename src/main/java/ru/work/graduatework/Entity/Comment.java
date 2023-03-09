package ru.work.graduatework.Entity;

import javax.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "primary_key")
    private int pk;
    @Column(name = "Comment_author")
    private int author;
    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "text")
    private String text;

    @ManyToOne                                  // Many-to-one communication
    @JoinColumn(name = "Ads_user_id")
    private Users user;

    // ----------------- block Getter's and Setter's ---------------------
    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
