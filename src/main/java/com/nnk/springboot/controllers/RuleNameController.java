package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exeception.ResourceNotFoundException;
import com.nnk.springboot.services.RuleNameService;
import com.nnk.springboot.utils.UserUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
public class RuleNameController {

    private final RuleNameService ruleNameService;

    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    @RequestMapping("/ruleName/list")
    public String home(Model model, Authentication authentication) {
        log.info("GET /ruleName/list called");
        List<RuleName> ruleNames = this.ruleNameService.findAllRuleNames();
        model.addAttribute("isAdmin", UserUtils.isAdmin(authentication));
        model.addAttribute("ruleNames",ruleNames);
        log.info("Display the ruleName list page");
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        log.info("GET /ruleName/add called -> display add ruleName form");
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        log.info("POST /ruleName/validate called -> start process to add a ruleName");
        if (result.hasErrors()){
            for (FieldError fieldError : result.getFieldErrors()) {
                log.info("Error in form validation on field {}", fieldError.getField());
            }
            return "ruleName/add";
        }
        this.ruleNameService.saveRuleName(ruleName);
        log.info("Process to add a ruleName end successfully");
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws ResourceNotFoundException {
        log.info("GET /ruleName/update/{} called", id);
        RuleName ruleName = this.ruleNameService.findRuleNameById(id);
        model.addAttribute("ruleName", ruleName);
        log.info("Display update form for ruleName with id {}", id);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                                 BindingResult result, Model model) {
        log.info("POST /ruleName/update/{} called -> start of the process to update the ruleName", id);
        if (result.hasErrors()){
            for (FieldError fieldError : result.getFieldErrors()) {
                log.info("Error in form validation on field {}", fieldError.getField());
            }
            return "ruleName/update";
        }
        this.ruleNameService.saveRuleName(ruleName);
        log.info("Process to update a ruleName with id {} end successfully", id);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        log.info("GET /ruleName/delete/{} called -> Start of the process to delete the ruleName with id {}", id, id);
        this.ruleNameService.deleteRuleNameById(id);
        log.info("Process to delete a ruleName end successfully");
        return "redirect:/ruleName/list";
    }

}
