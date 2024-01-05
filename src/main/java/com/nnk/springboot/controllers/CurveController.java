package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exeception.ResourceNotFoundException;
import com.nnk.springboot.services.CurvePointService;
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
public class CurveController {

    private final CurvePointService curvePointService;

    public CurveController(CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    @RequestMapping("/curvePoint/list")
    public String home(Model model, Authentication authentication) {
        log.info("GET /curvePoint/list called");
        List<CurvePoint> curvePoints = this.curvePointService.findAllCurvePoints();
        log.info("{} curve point(s) found", curvePoints.size());
        model.addAttribute("isAdmin", UserUtils.isAdmin(authentication));
        model.addAttribute("curvePoints", curvePoints);
        log.info("Display the curve point list page");
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
        log.info("GET /curvePoint/add called -> display add curve point form");
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        log.info("POST /curvePoint/validate called -> start process to add a curve point");
        if (result.hasErrors()) {
            for (FieldError fieldError : result.getFieldErrors()) {
                log.info("Error in form validation on field {}", fieldError.getField());
            }
            return "curvePoint/add";
        }
        this.curvePointService.saveCurvePoint(curvePoint);
        log.info("Process to add a curve point end successfully");
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws ResourceNotFoundException {
        log.info("GET /curvePoint/update/{} called", id);
        CurvePoint curvePointToUpdate = this.curvePointService.findCurvePointById(id);
        model.addAttribute("curvePoint", curvePointToUpdate);
        log.info("Display update form for curve point with id {}", id);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                            BindingResult result, Model model) {
        log.info("POST /curvePoint/update/{} called -> start of the process to update the curve point", id);
        if (result.hasErrors()) {
            for (FieldError fieldError : result.getFieldErrors()) {
                log.info("Error in form validation on field {}", fieldError.getField());
            }
            return "curvePoint/update";
        }
        this.curvePointService.saveCurvePoint(curvePoint);
        log.info("Process to update the curve point with id {} end successfully", id);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        log.info("GET /curvePoint/delete/{} called -> Start of the process to delete the curve point with id {}", id, id);
        this.curvePointService.deleteCurvePointById(id);
        log.info("Process to delete a curve point end successfully");
        return "redirect:/curvePoint/list";
    }

}
