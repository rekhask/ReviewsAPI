package com.udacity.course3.reviews.entity;


import javax.persistence.*;
import java.awt.*;

@Entity
@Table (name = "comments")
public class Comments {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "comment_id")
    private Integer Id;

    @ManyToOne
    @JoinColumn(name="review_id")
    private Review review;

    @Column (name="text")
    private String text;

    public Comments () {}

    public Comments (Review review, String text) {
        this.review = review;
        this.text = text;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
