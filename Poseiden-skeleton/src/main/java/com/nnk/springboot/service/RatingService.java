package com.nnk.springboot.service;

import com.nnk.springboot.domain.Dto.RatingDto;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exception.NotFoundException;

import java.util.List;

public interface RatingService {

    /**
     * Create a new rating
     *
     * @param ratingDto new rating to save
     * @return rating saved
     */
    Rating createRating(RatingDto ratingDto);

    /**
     * Get all ratings
     *
     * @return a list of all ratings
     */
    List<RatingDto> getAllRatings();

    /**
     * Get a rating
     *
     * @param id of the requested rating
     * @return rating found by id
     */
    RatingDto getRatingById(Integer id);

    /**
     * Update a rating
     *
     * @param id rating id to update
     * @param ratingDto rating's information to update
     * @return rating updated
     */
    Rating updateRating(Integer id, RatingDto ratingDto);

    /**
     * Delete a rating by id
     *
     * @param id ID of rating to delete
     */
    void deleteRating(Integer id);
}
