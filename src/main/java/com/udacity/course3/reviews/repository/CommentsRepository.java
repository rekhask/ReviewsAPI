package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository <Comments, Integer> {
    List<Comments> findByReviewId(Integer reviewId);
}
