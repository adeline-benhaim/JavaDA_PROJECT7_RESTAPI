package com.nnk.springboot.service;

import com.nnk.springboot.domain.Dto.RatingDto;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {
    private static final Logger logger = LoggerFactory.getLogger(RatingServiceImpl.class);

    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Create a new rating
     *
     * @param ratingDto new rating to save
     * @return rating saved
     */
    @Override
    public Rating createRating(RatingDto ratingDto) {
        logger.info("Create a new rating");
        Rating newRating = Rating.builder()
                .moodysRating(ratingDto.getMoodysRating())
                .sandPRating(ratingDto.getSandPRating())
                .fitchRating(ratingDto.getFitchRating())
                .orderNumber(ratingDto.getOrderNumber())
                .build();
        return ratingRepository.save(newRating);
    }
}
