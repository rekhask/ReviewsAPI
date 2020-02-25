package com.udacity.course3.reviews.model;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;


@Document ("reviews")
public class Review {

    @Id
    @Field("id")
    private String id;

    @Field("mysql_review_id")
    private Integer mySqlReviewId;
    private String title;
    private String text;

    @Field("product_id")
    private String productId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getMySqlReviewId() {
        return mySqlReviewId;
    }

    public void setMySqlReviewId(Integer mySqlReviewId) {
        this.mySqlReviewId = mySqlReviewId;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
