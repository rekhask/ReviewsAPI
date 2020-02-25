package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.Comments;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentsRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;



import javax.annotation.Resource;
import java.util.Optional;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@DataJpaTest
@Import({TestConfig.class})

public class ReviewsApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private ProductRepository productRepository;

	@Resource
	private ReviewRepository reviewRepository;

	@Autowired
	private CommentsRepository commentsRepository;


	@Test
	public void FindAllProducts() {
		List<Product> allProds = productRepository.findAll();
		Assert.assertEquals(2, allProds.size());
	}

	@Test
	public void FindProductReviewById(){
		List<Review> reviews = reviewRepository.findByProductId(1);
		Assert.assertEquals(2, reviews.size());
	}

	@Test
	public void FindCommentsForAReview(){
		List<Comments> comments = commentsRepository.findByReviewId(1);
		Assert.assertEquals(3, comments.size());
	}


}


