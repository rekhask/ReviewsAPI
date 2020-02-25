package com.udacity.course3.reviews;

import static org.assertj.core.api.Assertions.assertThat;

import com.mongodb.*;
import com.udacity.course3.reviews.model.Product;
import com.udacity.course3.reviews.model.Review;
import com.udacity.course3.reviews.repository.CommentsRepository;
import com.udacity.course3.reviews.repository.MongoReviewRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import com.mongodb.BasicDBObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Optional;

@DataMongoTest
@DirtiesContext
@RunWith(SpringRunner.class)
@Import({TestMongoConfig.class})


public class ReviewsMongoTest {

    @Autowired
    MongoReviewRepository mongoReviewRepositoryTest;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CommentsRepository commentsRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @org.junit.Test
    public void contextLoads() {
    }

    MongodExecutable mongodExecutable;

    @AfterEach
    void Clean(){
        mongodExecutable.stop();
    }

    @BeforeEach
    void setup() throws Exception {
        String ip = "mongodb://udacity:udacity@localhost";
        Integer port = 27017;


        IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.DEVELOPMENT)
                .net(new Net(ip, port, Network.localhostIsIPv6()))
                .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        mongodExecutable=starter.prepare(mongodConfig);
        mongodExecutable.start();
        mongoTemplate = new MongoTemplate(new MongoClient(ip, port), "product_review_test");
    }
    @Test
    public void manualTest(){

        DBObject objectToSave = BasicDBObjectBuilder.start()
                .add("text", "Test Review")
                .add("title", "test1")
                .add("mysql_review_id", 100)
                .get();
        mongoTemplate.save(objectToSave, "reviews");

        Query query = new Query();
        query.addCriteria(Criteria.where("mysql_review_id").is(100));

        assertThat(mongoTemplate.find(query, Review.class)).extracting("title").containsOnly("test1");


    }

}
