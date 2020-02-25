package com.udacity.course3.reviews.repository;


import com.udacity.course3.reviews.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MongoReviewRepository extends MongoRepository<Review, String> {

    Optional<Review> findByMySqlReviewId (int mySqlReviewId );
}
