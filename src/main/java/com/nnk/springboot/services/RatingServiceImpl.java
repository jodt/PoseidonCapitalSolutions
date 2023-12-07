package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exeception.ResourceNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RatingServiceImpl implements RatingService{

    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Rating> findAllRating() {
        log.info("Try to retrieve all ratings");
        return this.ratingRepository.findAll();
    }

    @Override
    public Rating saveRating(Rating rating) {
        log.info("Try to add or update a rating");
        return this.ratingRepository.save(rating);
    }

    @Override
    public void deleteRatingById(Integer id) {
        log.info("Try to delete rating with id : {}", id);
        this.ratingRepository.deleteById(id);
    }

    @Override
    public Rating findRatingById(Integer id) throws ResourceNotFoundException {
        log.info("Try to find rating with id {}", id);
        return this.ratingRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Rating with id " + id + " not found"));
    }

}
