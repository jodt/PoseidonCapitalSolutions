package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exeception.ResourceNotFoundException;
import com.nnk.springboot.services.RatingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @RequestMapping("/rating/list")
    public String home(Model model) {
        log.info("GET /rating/list called");
        List<Rating> ratings = this.ratingService.findAllRating();
        log.info("{} rating(s) found", ratings.size());
        model.addAttribute("ratings",ratings);
        log.info("Display the rating list page");
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        log.info("GET /rating/add called -> display add rating form");
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        log.info("POST /rating/validate called -> start process to add a rating");
        if(result.hasErrors()){
            for (FieldError fieldError : result.getFieldErrors()) {
                log.info("Error in form validation on field {}", fieldError.getField());
            }
            return "rating/add";
        }
        this.ratingService.saveRating(rating);
        log.info("Process to add a rating end successfully");
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws ResourceNotFoundException {
        Rating ratingToUpdate = this.ratingService.findRatingById(id);
        System.out.println(ratingToUpdate.toString());
        model.addAttribute("rating", ratingToUpdate);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        log.info("GET /rating/update/{} called -> start of the process to update the rating", id);
        if (result.hasErrors()){
            for (FieldError fieldError : result.getFieldErrors()) {
                log.info("Error in form validation on field {}", fieldError.getField());
            }
            return "rating/update";
        }
        this.ratingService.saveRating(rating);
        log.info("Process to update the rating with id {} end successfully", id);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        log.info("GET /rating/delete/{} called -> Start of the process to delete the rating with id {}", id, id);
        this.ratingService.deleteRatingById(id);
        log.info("Process to delete a rating end successfully");
        return "redirect:/rating/list";
    }

}
