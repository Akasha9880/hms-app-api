package com.hmsapp.controller;

import com.hmsapp.entity.Property;
import com.hmsapp.entity.Reviews;
import com.hmsapp.entity.User;
import com.hmsapp.payload.ReviewsDto;
import com.hmsapp.repository.ReviewsRepository;
import com.hmsapp.repository.UserRepository;
import com.hmsapp.service.ReviewService;
import com.hmsapp.repository.PropertyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {
    private ReviewService reviewService;
    private PropertyRepository propertyRepository;
    private UserRepository userRepository;
    private ReviewsRepository reviewRepository;

    public ReviewController(ReviewService reviewService, PropertyRepository propertyRepository, UserRepository userRepository,
                            ReviewsRepository reviewRepository) {
        this.reviewService = reviewService;
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @PostMapping
    public ResponseEntity<?> addReview(
            @RequestBody ReviewsDto reviewsDto,
            @RequestParam long propertyId,
            @RequestParam String username
            //@AuthenticationPrincipal
    ) {
        User user = userRepository.findByUsername(username).get();
        Property property = propertyRepository.findById(propertyId).get();
        Reviews reviewsStatus = reviewRepository.findByPropertyAndUser(property, user);

        if(reviewsStatus != null) {
            reviewsDto.setProperty(property);
            reviewsDto.setUser(user);
            ReviewsDto addReview = reviewService.addReview(reviewsDto);
            return new ResponseEntity<>(addReview, HttpStatus.OK);
        }
            return new ResponseEntity<>("You have already reviewed this property", HttpStatus.OK);

        }

    @GetMapping("user/reviews")
    public ResponseEntity<List<Reviews>> getReviews(

            @AuthenticationPrincipal User user) {
        List<Reviews> reviews = reviewService.viewMyReviews(user);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

}
