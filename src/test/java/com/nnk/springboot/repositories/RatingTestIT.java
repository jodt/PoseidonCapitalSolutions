package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class RatingTest {

    @Autowired
    private RatingRepository ratingRepository;


    @Test
    public void ratingTest() {
        Rating rating;

        rating = Rating.builder()
                .moodysRating("Moodys Rating")
                .sandPRating("Sand PRating")
                .fitchRating("Fitch Rating")
                .orderNumber(10)
                .build();

        // Save
        rating = ratingRepository.save(rating);
        Assert.assertNotNull(rating.getId());
        Assert.assertTrue(rating.getOrderNumber() == 10);

        // Update
        rating.setOrderNumber(20);
        rating = ratingRepository.save(rating);
        Assert.assertTrue(rating.getOrderNumber() == 20);

        // Find
        List<Rating> listResult = ratingRepository.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = rating.getId();
        ratingRepository.delete(rating);
        Optional<Rating> ratingList = ratingRepository.findById(id);
        Assert.assertFalse(ratingList.isPresent());
    }

}
