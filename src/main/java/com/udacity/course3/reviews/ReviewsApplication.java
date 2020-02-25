package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.Comments;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@ComponentScan
public class ReviewsApplication {

	public static void main(String[] args) {

		SpringApplication.run(ReviewsApplication.class, args);
	}


	@Bean
	public CommandLineRunner loadRecords(ProductRepository productRepository, ReviewRepository reviewRepository, CommentsRepository commentsRepository, MongoReviewRepository mongoReviewRepository) {
		return(args)->
		{
			// Loading data to mysql using JPA Repositories
			mySqlDataLoad(productRepository, reviewRepository, commentsRepository, mongoReviewRepository);

		};
	}

	private static void mySqlDataLoad(ProductRepository productRepository, ReviewRepository reviewRepository, CommentsRepository commentsRepository, MongoReviewRepository mongoReviewRepository){
		//Create product
		Product prod1 = new Product("Pink Teddy Bear", "This is a great buy! Good quality and very soft");
		Review prod1Review1 = new Review("Good Buy!", "This is a great buy! Good quality and very soft", prod1);
		Review prod1Review2 = new Review("Absolutely Ripoff", "Avoid this seller and product! Pictures does not resemble the actual product. Avoid by all means!", prod1);
		prod1 = productRepository.save(prod1);

		// Add reviews in MySql and MongoDb
		prod1Review1 = reviewRepository.save(prod1Review1);
		mongoReviewRepository.save(buildMongoReview(prod1Review1, prod1.getId().toString()));

		prod1Review2 = reviewRepository.save(prod1Review2);
		mongoReviewRepository.save(buildMongoReview(prod1Review2, prod1.getId().toString()));

		Comments prod1Review1Comment1 = new Comments(prod1Review1, "Thanks for your inputs. IT helped!");
		Comments prod1Review1Comment2 = new Comments(prod1Review1, "Thanks for your inputs. I will surely buy this for my grandchild");
		Comments prod1Review1Comment3 = new Comments(prod1Review1, "Thanks for your inputs!!");
		prod1Review1Comment1 = commentsRepository.save(prod1Review1Comment1);
		prod1Review1Comment2 = commentsRepository.save(prod1Review1Comment2);
		prod1Review1Comment3 = commentsRepository.save(prod1Review1Comment3);

		Comments prod1Review2Comment1 = new Comments(prod1Review2, "Prod 1 Review 2 Comment 1");
		Comments prod1Review2Comment2 = new Comments(prod1Review2, "Prod 1 Review 2 Comment 2");
		prod1Review2Comment1 = commentsRepository.save(prod1Review2Comment1);
		prod1Review2Comment2 = commentsRepository.save(prod1Review2Comment2);

		Product prod2 = new Product("Blue Teddy Bear", "This is a Blue 10 inch teddy bear.");
		Review prod2Review1 = new Review("Good Buy!", "This Blue bear is a great buy! Good quality and very soft", prod2);
		Review prod2Review2 = new Review("Absolutely Ripoff", "Avoid this seller and this Blue bear! Pictures does not resemble the actual product. Avoid by all means!", prod2);

		prod2 = productRepository.save(prod2);

		// Add reviews in MySql and MongoDb
		prod2Review1 = reviewRepository.save(prod2Review1);
		mongoReviewRepository.save(buildMongoReview(prod2Review1, prod2.getId().toString()));

		prod2Review2 = reviewRepository.save(prod2Review2);
		mongoReviewRepository.save(buildMongoReview(prod2Review2, prod2.getId().toString()));



		Comments prod2Review1Comment1 = new Comments(prod2Review1, "Prod 2 Review 1 Comment 1");
		Comments prod2Review1Comment2 = new Comments(prod2Review1, "Prod 2 Review 1 Comment 2");
		prod2Review1Comment1 = commentsRepository.save(prod2Review1Comment1);
		prod2Review1Comment2 = commentsRepository.save(prod2Review1Comment2);

		Comments prod2Review2Comment1 = new Comments(prod2Review2, "Prod 2 Review 2 Comment 1");
		Comments prod2Review2Comment2 = new Comments(prod2Review2, "Prod 2 Review 2 Comment 2");
		prod2Review2Comment1 = commentsRepository.save(prod2Review2Comment1);
		prod2Review2Comment2 = commentsRepository.save(prod2Review2Comment2);
	}

	private static com.udacity.course3.reviews.model.Review buildMongoReview (Review reviewObj, String prodId) {

		com.udacity.course3.reviews.model.Review review = new com.udacity.course3.reviews.model.Review();
		review.setMySqlReviewId(reviewObj.getId());
		review.setTitle(reviewObj.getTitle());
		review.setText(reviewObj.getText());
		review.setProductId(prodId);
		return review;
	}
}