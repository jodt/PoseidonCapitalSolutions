package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exeception.ResourceNotFoundException;
import com.nnk.springboot.services.BidListService;
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
public class BidListController {

    private final BidListService bidListService;

    public BidListController(BidListService bidListService) {
        this.bidListService = bidListService;
    }

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        log.info("GET /bidList/list called");
        List<BidList> bidLists = this.bidListService.getAllBid();
        log.info("{} bid(s) found", bidLists.size());
        model.addAttribute("bidLists", bidLists);
        log.info("Display the bid list page");
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        log.info("GET /bidList/add called -> display add bid form");
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        log.info("POST bidList/validate called -> start process to add a bid");
        if (result.hasErrors()) {
            for (FieldError fieldError : result.getFieldErrors()) {
                log.info("Error in form validation on field {}", fieldError.getField());
            }
            return "bidList/add";
        }
        this.bidListService.saveBid(bid);
        log.info("Process to add a bid end successfully");
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws ResourceNotFoundException {
        log.info("GET /bidList/update/{} called", id);
        BidList bidToUpdate = this.bidListService.findById(id);
        model.addAttribute("bidList", bidToUpdate);
        log.info("Display update form for bid with id {}", id);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        log.info("GET /bidList/update/{} called -> start of the process to update the bid", id);
        if (result.hasErrors()) {
            for (FieldError fieldError : result.getFieldErrors()) {
                log.info("Error in form validation on field {}", fieldError.getField());
            }
            return "bidList/update";
        }
        bidListService.saveBid(bidList);
        log.info("Process to update a bid with id {} end successfully", id);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        log.info("GET /bidList/delete/{} called -> Start of the process to delete the bid with id {}", id, id);
        this.bidListService.deleteBidById(id);
        log.info("Process to delete a bid end successfully");
        return "redirect:/bidList/list";
    }

}
