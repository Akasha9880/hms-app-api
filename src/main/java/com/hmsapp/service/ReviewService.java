package com.hmsapp.service;

import com.hmsapp.entity.Property;
import com.hmsapp.entity.Reviews;
import com.hmsapp.entity.User;
import com.hmsapp.payload.PropertyDto;
import com.hmsapp.payload.ReviewsDto;
import com.hmsapp.payload.UserDto;
import com.hmsapp.repository.PropertyRepository;
import com.hmsapp.repository.ReviewsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private ReviewsRepository reviewsRepository;
    private ModelMapper modelMapper;
    private PropertyRepository propertyRepository;
    public ReviewService(ReviewsRepository reviewRepository,ModelMapper modelMapper) {
        this.reviewsRepository = reviewRepository;
        this .modelMapper = modelMapper;
    }

    Reviews mapToEntity(ReviewsDto dto){
        Reviews reviews = modelMapper.map(dto, Reviews.class);
        return reviews;
    }

    ReviewsDto mapToDto(Reviews reviews){
        ReviewsDto reviewsDto = modelMapper.map(reviews, ReviewsDto.class);
        return reviewsDto;
    }

    public ReviewsDto addReview(ReviewsDto dto){
        Reviews reviews = mapToEntity(dto);

        Reviews savedReviews = reviewsRepository.save(reviews);
        ReviewsDto reviewsDto = mapToDto(savedReviews);
    return reviewsDto;
    }

    public List<Reviews> viewMyReviews(User user){
        List<Reviews> reviews = reviewsRepository.findByUser(user);
    return reviews;
    }

}
