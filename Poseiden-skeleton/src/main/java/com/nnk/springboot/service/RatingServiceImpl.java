package com.nnk.springboot.service;

import com.nnk.springboot.mapper.MapperDto;
import com.nnk.springboot.domain.Dto.RatingDto;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Get all ratings
     *
     * @return a list of all ratings
     */
    @Override
    public List<RatingDto> getAllRatings() {
        logger.info("Get all ratings");
        List<Rating> ratingList = ratingRepository.findAllByOrderByIdDesc();
        return ratingList
                .stream().map(MapperDto::convertToRatingDto)
                .collect(Collectors.toList());
    }

    /**
     * Get a rating
     * @param id of the requested rating
     * @return rating found by id
     */
    @Override
    public  RatingDto getRatingById(Integer id) {
        logger.info("Get a rating by ID");
        if (!ratingRepository.existsById(id)){
            logger.error("Unable to get rating by id : " + id + " because doesn't exist");
            throw new NotFoundException("Rating id doesn't exist");
        }
        Rating rating = ratingRepository.getById(id);
        return MapperDto.convertToRatingDto(rating);
    }

    /**
     * Update a rating
     * @param id rating id to update
     * @param ratingDto rating's information to update
     * @return rating updated
     */
    @Override
    @Transactional
    public Rating updateRating(Integer id, RatingDto ratingDto) {
        logger.info("Get a rating by ID");
        if (!ratingRepository.existsById(id)){
            logger.error("Unable to get rating by id : " + id + " because doesn't exist");
            throw new NotFoundException("Rating id doesn't exist");
        }
        Rating ratingToUpdate = ratingRepository.getById(id);
        ratingToUpdate.setMoodysRating(ratingDto.getMoodysRating());
        ratingToUpdate.setSandPRating(ratingDto.getSandPRating());
        ratingToUpdate.setFitchRating(ratingDto.getFitchRating());
        ratingToUpdate.setOrderNumber(ratingDto.getOrderNumber());
        logger.info("Rating updated");
        return ratingRepository.save(ratingToUpdate);
    }

    /**
     * Delete a rating by id
     *
     * @param id ID of rating to delete
     */
    @Override
    @Transactional
    public void deleteRating(Integer id) {
        logger.info("Delete rating : " + id);
        ratingRepository.deleteById(id);
    }
}
