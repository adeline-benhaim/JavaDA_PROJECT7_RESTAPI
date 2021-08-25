package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Dto.CurvePointDto;
import com.nnk.springboot.service.CurvePointService;
import com.nnk.springboot.exception.NotFoundException;
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
        try {
            CurvePointDto curvePointDtoToUpdate = curvePointService.getCurvePointById(id);
            model.addAttribute("curvePointDto", curvePointDtoToUpdate);
            return "curvePoint/update";
        } catch (NotFoundException e) {
            return "notFound";
        }
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePointDto curvePointDto,
                            BindingResult result, Model model) {
        try {
            if (!result.hasErrors()) {
                curvePointService.updateCurvePoint(id, curvePointDto);
                return "redirect:/curvePoint/list";
            }
            model.addAttribute("curvePointDto", curvePointDto);
            return "curvePoint/update";
        } catch (NotFoundException e) {
            return "notFound";
        }
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id) {
        curvePointService.deleteCurvePoint(id);
        return "redirect:/curvePoint/list";
    }
}
