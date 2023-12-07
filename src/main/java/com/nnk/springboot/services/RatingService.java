package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exeception.ResourceNotFoundException;

import java.util.List;

public interface RatingService {

    List<Rating> findAllRating();
    Rating saveRating(Rating rating);
    void deleteRatingById(Integer id);
    Rating findRatingById(Integer id) throws ResourceNotFoundException;

}
