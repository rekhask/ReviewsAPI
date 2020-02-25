package com.udacity.course3.reviews.entity;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="product_id")
    private Integer id;

    @Column (name = "name")
    private String name;

    @Column (name = "description")
    private String description;

    @OneToMany (mappedBy = "product", cascade = CascadeType.PERSIST)
    private List<Review> reviews;

    public Product(){}

    public Product (String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
