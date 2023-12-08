package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exeception.ResourceNotFoundException;
import com.nnk.springboot.services.TradeService;
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
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @RequestMapping("/trade/list")
    public String home(Model model) {
        log.info("GET /trade/list called");
        List<Trade> trades = this.tradeService.findAllTrades();
        model.addAttribute("trades", trades);
        log.info("Display the trade list page");
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        log.info("GET /trade/add called -> display add trade form");
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        log.info("POST /trade/validate called -> start process to add a trade");
        if(result.hasErrors()){
            for (FieldError fieldError : result.getFieldErrors()) {
                log.info("Error in form validation on field {}", fieldError.getField());
            }
            return "trade/add";
        }
        this.tradeService.saveTrade(trade);
        log.info("Process to add a trade end successfully");
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws ResourceNotFoundException {
        log.info("GET /trade/update/{} called", id);
        Trade tradeToUpdate = this.tradeService.findTradeById(id);
        model.addAttribute("trade", tradeToUpdate);
        log.info("Display update form for trade with id {}", id);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                              BindingResult result, Model model) {
        log.info("POST /trade/update/{} called -> start of the process to update the trade", id);
        if(result.hasErrors()){
            return "trade/update";
        }
        this.tradeService.saveTrade(trade);
        log.info("Process to update a trade with id {} end successfully", id);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        log.info("GET /trade/delete/{} called -> Start of the process to delete the trade with id {}", id, id);
        this.tradeService.deleteTradeById(id);
        log.info("Process to delete a trade end successfully");
        return "redirect:/trade/list";
    }

}
