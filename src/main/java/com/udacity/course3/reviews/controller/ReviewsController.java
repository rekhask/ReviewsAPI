package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.MongoReviewRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.net.ssl.HttpsURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
@EnableMongoRepositories (repositoryBaseClass = MongoReviewRepository.class)
public class ReviewsController {




    // TODO: Wire JPA repositories here
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MongoReviewRepository mongoReviewRepository;

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createReviewForProduct(@PathVariable("productId") Integer productId, @RequestBody Review review) {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            Product validProduct = product.get();
            review.setProduct(validProduct);
            // save review object in MySql db
            reviewRepository.save(review);

            // save review object in MongdDb
            com.udacity.course3.reviews.model.Review mongoReview = new com.udacity.course3.reviews.model.Review();
            mongoReview.setProductId(productId.toString());
            mongoReview.setMySqlReviewId(review.getId());
            mongoReview.setTitle(review.getTitle());
            mongoReview.setText(review.getText());
            mongoReviewRepository.save(mongoReview);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(review.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);

         for (com.udacity.course3.reviews.entity.Review eachReview : reviews) {
            Optional<com.udacity.course3.reviews.model.Review> eachMongoReview = mongoReviewRepository.findByMySqlReviewId(eachReview.getId());
            if (eachMongoReview.isPresent()) {
                eachReview.setText(eachMongoReview.get().getText());
                eachReview.setTitle( eachMongoReview.get().getTitle());
            }
        }

        if (!reviews.isEmpty()) {
            return ResponseEntity.ok().body(reviews);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}


