package com.udacity.course3.reviews.entity;

import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "review")
public class Review {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "review_id")
    private Integer id;

    @Column (name = "title")
    private String title;

    @Column (name = "text")
    private  String text;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany (mappedBy = "review", cascade = CascadeType.PERSIST)
    private List<Comments> comments;

    public Review() {}

    public Review (String title, String text, Product product) {
        this.title = title;
        this.text = text;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
