package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RatingController.class)
@WithMockUser("john@test.com")
class RatingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RatingService ratingService;

    private Rating rating;

    @BeforeEach
    void init() {
        rating = Rating.builder()
                .moodysRating("moodys")
                .sandPRating("S&P")
                .fitchRating("fitch")
                .orderNumber(1)
                .build();
    }

    @Test
    @DisplayName("Should display the rating list")
    void shouldDisplayRatingListPage() throws Exception {

        when(this.ratingService.findAllRating()).thenReturn(List.of(rating));

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("isAdmin", false))
                .andExpect(model().attribute("ratings", hasSize(1)))
                .andExpect(model().attribute("ratings", allOf(hasItem(rating))))
                .andExpect(view().name("rating/list"));

        verify(ratingService).findAllRating();
    }

    @Test
    @DisplayName("Should display the add rating form")
    void shouldDisplayAddRatingForm() throws Exception {

        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    @DisplayName("should save the rating and redirect to the rating listing page")
    void shouldValidateAndSaveARating() throws Exception {

        mockMvc.perform(post("/rating/validate")
                        .with(csrf())
                        .flashAttr("rating", rating))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    @DisplayName("should not save the rating  -> rating has error fields")
    void shouldNotValidateAndSaveARating() throws Exception {

        mockMvc.perform(post("/rating/validate")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeHasFieldErrors("rating", "moodysRating", "sandPRating", "fitchRating"));
    }

    @Test
    @DisplayName("should display the rating update form with the rating to be updated")
    void shouldShowUpdateForm() throws Exception {

        when(this.ratingService.findRatingById(1)).thenReturn(rating);

        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attribute("rating", rating));

        verify(ratingService).findRatingById(1);
    }

    @Test
    @DisplayName("should update the rating and redirect to the rating listing page")
    void ShouldUpdateRating() throws Exception {

        mockMvc.perform(post("/rating/update/1")
                        .with(csrf())
                        .flashAttr("rating", rating))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    @DisplayName("should not update the rating -> rating has error fields")
    void ShouldNotUpdateRating() throws Exception {

        mockMvc.perform(post("/rating/update/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeHasFieldErrors("rating", "moodysRating", "sandPRating", "fitchRating"));
    }

    @Test
    @DisplayName("should delete the rating and redirect to the rating listing page")
    void deleteRating() throws Exception {

        doNothing().when(this.ratingService).deleteRatingById(1);

        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));

        verify(ratingService).deleteRatingById(1);
    }

}