package com.nnk.springboot.service;

import com.nnk.springboot.domain.Dto.RatingDto;
import com.nnk.springboot.domain.Rating;

import java.util.List;

public interface RatingService {

    /**
     * Create a new rating
     *
     * @param ratingDto new rating to save
     * @return rating saved
     */
   Rating createRating(RatingDto ratingDto);

    List<RatingDto> getAllRatings();
}
