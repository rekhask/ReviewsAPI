package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Comments;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentsRepository;
import com.udacity.course3.reviews.repository.MongoReviewRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    // TODO: Wire needed JPA repositories here
    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongoReviewRepository mongoReviewRepository;

    @Autowired
    private ProductRepository productRepository;
    /**
     * Creates a comment for a review.
     * <p>
     * 1. Add argument for comment entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, save comment.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createCommentForReview(@PathVariable("reviewId") Integer reviewId, @RequestBody Comments comments) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isPresent()) {
            Review validReview = review.get();
            comments.setReview(validReview);
            commentsRepository.save(comments);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(comments.getId()).toUri();
            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * List comments for a review.
     * <p>
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        Optional<Review> currentReview = reviewRepository.findById(reviewId);

        // if review exists and check and return the comments or not found status.
        if (currentReview.isPresent()) {
            List<Comments> comments = commentsRepository.findByReviewId(reviewId);

            for (com.udacity.course3.reviews.entity.Comments eachComment : comments) {
                Optional<com.udacity.course3.reviews.model.Review> eachMongoReview = mongoReviewRepository.findByMySqlReviewId(eachComment.getReview().getId());
                if (eachMongoReview.isPresent()) {
                    Review eachMySqlReview = new Review();
                    eachMySqlReview.setTitle(eachMongoReview.get().getTitle());
                    eachMySqlReview.setText(eachMongoReview.get().getText());
                    eachMySqlReview.setId(eachMongoReview.get().getMySqlReviewId());

                    Optional<Product> prod = productRepository.findById(Integer.parseInt(eachMongoReview.get().getProductId().toString()));
                    if (prod.isPresent()) {
                        eachMySqlReview.setProduct(prod.get());
                    }
                    eachComment.setReview(eachMySqlReview);

                }
            }

            if (!comments.isEmpty()) {
                return ResponseEntity.ok().body(comments);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }
}

