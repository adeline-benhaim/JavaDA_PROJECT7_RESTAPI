package com.nnk.springboot.service;

import com.nnk.springboot.config.DataSourceTest;
import com.nnk.springboot.domain.Dto.RatingDto;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)

public class RatingServiceImplTest {

    @Mock
    RatingRepository ratingRepository;
    @InjectMocks
    RatingServiceImpl ratingService;
    @InjectMocks
    DataSourceTest dataSourceTest;

    @BeforeEach
    void init() {
        dataSourceTest.clearRatingListMocked();
        dataSourceTest.createRatingListMocked();
    }

    @Test
    @DisplayName("Create new rating")
    void createRatingTest() {

        //GIVEN
        RatingDto ratingDto = RatingDto.builder().id(4).moodysRating("moodysRating4").sandPRating("sandPRating4").fitchRating("fitchRating4").orderNumber(4).build();

        //WHEN
        ratingService.createRating(ratingDto);

        //THEN
        Mockito.verify(ratingRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Get all ratings")
    void getAllRatingsTest() {

        //GIVEN
        List<Rating> ratingList = dataSourceTest.getRatingListMocked();
        Mockito.when(ratingRepository.findAllByOrderByIdDesc()).thenReturn(ratingList);

        //THEN
        List<RatingDto> ratingDtoList = ratingService.getAllRatings();

        //WHEN
        assertEquals(ratingList.size(), ratingDtoList.size());
        assertEquals(ratingList.get(0).getMoodysRating(), ratingDtoList.get(0).getMoodysRating());
    }

    @Test
    @DisplayName("Get existing rating by id")
    void getExistingRatingByIdTest() {

        //GIVEN
        Mockito.when(ratingRepository.existsById(1)).thenReturn(true);
        Rating rating = dataSourceTest.getRatingListMocked().get(0);
        Mockito.when(ratingRepository.getById(1)).thenReturn(rating);

        //THEN
        RatingDto ratingDto = ratingService.getRatingById(1);

        //WHEN
        assertEquals(rating.getMoodysRating(), ratingDto.getMoodysRating());
    }

    @Test
    @DisplayName("Get unknown rating by id throw NotFoundException")
    void getUnknownRatingByIdTest() {

        //GIVEN
        Mockito.when(ratingRepository.existsById(5)).thenReturn(false);

        //THEN

        //WHEN
        assertThrows(NotFoundException.class, () -> ratingService.getRatingById(5));
    }

    @Test
    @DisplayName("Update an existing rating")
    void updateExistingRatingTest() {

        //GIVEN
        Mockito.when(ratingRepository.existsById(1)).thenReturn(true);
        Rating ratingToUpdate = dataSourceTest.getRatingListMocked().get(0);
        Mockito.when(ratingRepository.getById(1)).thenReturn(ratingToUpdate);
        RatingDto ratingDto = RatingDto.builder().id(1).moodysRating("moodysRating1Updated").sandPRating("sandPRating1Updated").fitchRating("fitchRating1Updated").orderNumber(1).build();

        //THEN
        ratingService.updateRating(1, ratingDto);

        //WHEN
        Mockito.verify(ratingRepository, Mockito.times(1)).save(ratingToUpdate);
        assertEquals("moodysRating1Updated", ratingToUpdate.getMoodysRating());
    }

    @Test
    @DisplayName("Try to update an unknown rating throw NotFoundException")
    void updateUnknownRatingTest() {

        //GIVEN
        Mockito.when(ratingRepository.existsById(5)).thenReturn(false);
        RatingDto ratingDto = RatingDto.builder().id(1).moodysRating("moodysRating1Updated").sandPRating("sandPRating1Updated").fitchRating("fitchRating1Updated").orderNumber(1).build();

        //THEN

        //WHEN
        assertThrows(NotFoundException.class, () -> ratingService.updateRating(5, ratingDto));
    }

    @Test
    @DisplayName("Delete a rating")
    void deleteRatingTest() {

        //GIVEN

        //WHEN
        ratingService.deleteRating(1);

        //THEN
        Mockito.verify(ratingRepository, Mockito.times(1)).deleteById(any());
    }
}
