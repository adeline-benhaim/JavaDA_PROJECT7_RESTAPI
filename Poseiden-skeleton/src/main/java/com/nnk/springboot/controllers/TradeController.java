package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Dto.TradeDto;
import com.nnk.springboot.service.TradeService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TradeController {

    @Autowired
    TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model) {
        List<TradeDto> tradeDtoList = tradeService.getAllTrades();
        model.addAttribute("tradeDtoList", tradeDtoList);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Model model) {
        TradeDto tradeDto = new TradeDto();
        model.addAttribute("tradeDto", tradeDto);
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid @ModelAttribute TradeDto tradeDto, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            tradeService.createTrade(tradeDto);
            return "redirect:/trade/list";
        }
        model.addAttribute("tradeDto", tradeDto);
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            TradeDto tradeDtoToUpdate = tradeService.getTradeById(id);
            model.addAttribute("tradeDto", tradeDtoToUpdate);
            return "trade/update";
        } catch (NotFoundException e) {
            return "notFound";
        }
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid TradeDto tradeDto, BindingResult result, Model model) {
        try {
            if (!result.hasErrors()) {
                tradeService.updateTrade(id, tradeDto);
                return "redirect:/trade/list";
            }
            model.addAttribute("tradeDto", tradeDto);
            return "trade/update";
        } catch (NotFoundException e) {
            return "notFound";
        }
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.deleteTrade(id);
        return "redirect:/trade/list";
    }
}
