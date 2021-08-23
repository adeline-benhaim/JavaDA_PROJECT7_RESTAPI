package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    /**
     * Find a list of ratings sorted by id desc
     *
     * @return a list of all ratings sorted by id desc
     */
    List<Rating> findAllByOrderByIdDesc();

    /**
     * Find a rating
     *
     * @param id of the requested rating
     * @return rating found by id
     */
    Rating findRatingById(Integer id);

}
