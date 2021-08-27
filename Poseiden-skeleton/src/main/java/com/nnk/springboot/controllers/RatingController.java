package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Dto.RatingDto;
import com.nnk.springboot.service.RatingService;
import com.nnk.springboot.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model) {
        List<RatingDto> ratingDtoList = ratingService.getAllRatings();
        model.addAttribute("ratingDtoList", ratingDtoList);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        RatingDto ratingDto = new RatingDto();
        model.addAttribute("ratingDto", ratingDto);
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid @ModelAttribute RatingDto ratingDto, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ratingService.createRating(ratingDto);
            return "redirect:/rating/list";
        }
        model.addAttribute("ratingDto", ratingDto);
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            RatingDto ratingDtoToUpdate = ratingService.getRatingById(id);
            model.addAttribute("ratingDto", ratingDtoToUpdate);
            return "rating/update";
        } catch (NotFoundException e) {
            return "notFound";
        }
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid RatingDto ratingDto,
                               BindingResult result, Model model) {
        try {
            if (!result.hasErrors()) {
                ratingService.updateRating(id, ratingDto);
                return "redirect:/rating/list";
            }
            model.addAttribute("ratingDto", ratingDto);
            return "rating/update";
        } catch (NotFoundException e) {
            return "notFound";
        }
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.deleteRating(id);
        return "redirect:/rating/list";
    }
}
