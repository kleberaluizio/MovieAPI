package com.kleberaluizio.movies;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> addReview(@RequestBody Map<String, String> payLoad){
        return new ResponseEntity<>(reviewService.createReview(payLoad.get("reviewBody"),payLoad.get("imdbId")), HttpStatus.CREATED);
    }

    @DeleteMapping("/{reviewId}")
    @Transactional
    public ResponseEntity eraseReview(@PathVariable ObjectId reviewId){
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{reviewId}")
    @Transactional
    public ResponseEntity editReview(@PathVariable ObjectId reviewId, @RequestBody Map<String, String> payLoad){
        return new ResponseEntity<>(reviewService.updateReview(reviewId, payLoad.get("reviewBody")), HttpStatus.OK);
    }

}
