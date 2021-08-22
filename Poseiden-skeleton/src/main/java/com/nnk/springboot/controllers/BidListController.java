package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Dto.BidListDto;
import com.nnk.springboot.service.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
public class BidListController {

    @Autowired
    private BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        List<BidListDto> bidList = bidListService.getAllBidList();
        model.addAttribute("bidList", bidList);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        BidListDto bidListDto = new BidListDto();
        model.addAttribute("bidListDto", bidListDto);
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid @ModelAttribute BidListDto bidListDto, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            bidListService.createBidList(bidListDto);
            return "redirect:/bidList/list";
        }
        model.addAttribute("bidListDto", bidListDto);
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidListDto bidListToUpdate = bidListService.getBidListById(id);
        model.addAttribute("bidListDto", bidListToUpdate);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListDto bidListDto, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            bidListService.updateBidList(id, bidListDto);
            return "redirect:/bidList/list";
        }
        model.addAttribute("bidListDto", bidListDto);
        return "/bidList/update/{id}";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.deleteBidList(id);
        return "redirect:/bidList/list";
    }
}
