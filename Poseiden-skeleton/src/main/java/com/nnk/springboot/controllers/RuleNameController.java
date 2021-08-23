package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Dto.RuleNameDto;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RuleNameController {

    @Autowired
    private RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        List<RuleNameDto> ruleNameDtoList = ruleNameService.getAllRuleNames();
        model.addAttribute("ruleNameDtoList", ruleNameDtoList);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        RuleNameDto ruleNameDto = new RuleNameDto();
        model.addAttribute("ruleNameDto", ruleNameDto);
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid @ModelAttribute RuleNameDto ruleNameDto, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ruleNameService.createRuleName(ruleNameDto);
            return "redirect:/ruleName/list";
        }
        model.addAttribute("ruleNameDto", ruleNameDto);
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleNameDto ruleNameDtoToUpdate = ruleNameService.getRuleNameById(id);
        model.addAttribute("ruleNameDtoToUpdate", ruleNameDtoToUpdate);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleNameDto ruleNameDto,
                             BindingResult result, Model model) {
        if (!result.hasErrors()) {
            ruleNameService.updateRuleName(id, ruleNameDto);
            return "redirect:/ruleName/list";
        }
        model.addAttribute("ruleNameDto", ruleNameDto);
        return "redirect:/ruleName/update/{id}";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.deleteRuleName(id);
        return "redirect:/ruleName/list";
    }
}
