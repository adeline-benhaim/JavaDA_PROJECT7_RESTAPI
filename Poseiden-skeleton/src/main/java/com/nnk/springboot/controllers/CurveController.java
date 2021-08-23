package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Dto.CurvePointDto;
import com.nnk.springboot.service.CurvePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CurveController {

    @Autowired
    private CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        List<CurvePointDto> curvePointDtoList = curvePointService.getAllCurvePoints();
        model.addAttribute("curvePointDtoList", curvePointDtoList);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(Model model) {
        CurvePointDto curvePointDto = new CurvePointDto();
        model.addAttribute("curvePointDto", curvePointDto);
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid @ModelAttribute CurvePointDto curvePointDto, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            curvePointService.createCurvePoint(curvePointDto);
            return "redirect:/curvePoint/list";
        }
        model.addAttribute("curvePointDto", curvePointDto);
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        return "redirect:/curvePoint/list";
    }
}
