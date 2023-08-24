package com.kleberaluizio.movies;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Review createReview(String reviewBody, String imdbId){
        Review review = reviewRepository.insert(new Review(reviewBody, imdbId));

        mongoTemplate.update(Movie.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review))
                .first();

        return review;
    }

    public void deleteReview(ObjectId reviewId){

        Review review = reviewRepository.findReviewById(reviewId);

        mongoTemplate.updateFirst(
                Query.query(Criteria.where("imdbId").is(review.getImdbId())),
                new Update().pull("reviewIds", review.getId()),
                Movie.class);

        reviewRepository.removeReviewById(reviewId);

    }

    public Review updateReview(ObjectId reviewId, String reviewBody) {

        Review review = reviewRepository.findReviewById(reviewId);
        review.setBody(reviewBody);
        reviewRepository.save(review);

        return review;
    }
}
