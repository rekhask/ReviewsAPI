package com.udacity.course3.reviews;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.udacity.course3.reviews.entity.Comments;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentsRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Optional;

@Configuration
public class TestConfig {

	@Bean
	public MongoTemplate mongoTemplate() throws Exception
	{
		MongoClient mongo = MongoClients.create("mongodb://udacity:udacity@localhost:27017/product_review_test");
		return new MongoTemplate(mongo, "product_review_test");
	}
}