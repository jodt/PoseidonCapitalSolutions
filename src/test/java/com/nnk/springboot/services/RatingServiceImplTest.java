package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exeception.ResourceNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceImplTest {

    @Mock
    RatingRepository ratingRepository;

    @InjectMocks
    RatingServiceImpl ratingService;

    private Rating rating;

    @BeforeEach
    void init() {
        rating = Rating.builder()
                .moodysRating("moodysRating test")
                .sandPRating("sandPRating test")
                .fitchRating("fitchRating test")
                .orderNumber(10)
                .build();
    }

    @Test
    @DisplayName("Should find all ratings")
    void shouldFindAllRating() {

        when(this.ratingRepository.findAll()).thenReturn(List.of(rating));

        List<Rating> result = this.ratingService.findAllRating();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(rating, result.get(0));

        verify(ratingRepository).findAll();
    }

    @Test
    @DisplayName("Should save or update a rating")
    void shouldSaveRating() {
        Rating ratingSaved = rating;
        ratingSaved.setId(5);

        when(this.ratingRepository.save(rating)).thenReturn(ratingSaved);

        Rating result = this.ratingService.saveRating(rating);

        assertNotNull(result);
        assertEquals(ratingSaved, result);

        verify(ratingRepository).save(rating);
    }

    @Test
    @DisplayName("Should delete a rating by id")
    void shouldDeleteRatingById() {

        doNothing().when(this.ratingRepository).deleteById(5);

        this.ratingService.deleteRatingById(5);

        verify(ratingRepository).deleteById(5);
    }

    @Test
    @DisplayName("Should find a rating by id")
    void shouldFindRatingById() throws ResourceNotFoundException {
        Rating ratingSaved = rating;
        ratingSaved.setId(5);

        when(this.ratingRepository.findById(5)).thenReturn(Optional.of(ratingSaved));

        Rating result = this.ratingService.findRatingById(5);

        assertNotNull(result);
        assertEquals(ratingSaved, result);

        verify(ratingRepository).findById(5);
    }

    @Test
    @DisplayName("Should not find a rating by id -> ResourceNotFoundException")
    void shouldNotFindRatingById() {
        Rating ratingSaved = rating;
        ratingSaved.setId(5);

        when(this.ratingRepository.findById(10)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> this.ratingService.findRatingById(10));

        verify(ratingRepository).findById(10);
    }

}