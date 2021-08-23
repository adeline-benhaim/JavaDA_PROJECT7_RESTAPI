package com.nnk.springboot.service;

import com.nnk.springboot.domain.Dto.RuleNameDto;
import com.nnk.springboot.domain.RuleName;

import java.util.List;

public interface RuleNameService {

    /**Create a new rule name
     *
     * @param ruleNameDto new rule name to save
     * @return rule name saved
     */
    RuleName createRuleName(RuleNameDto ruleNameDto);

    /**
     * Get all rules
     *
     * @return a list of all rules
     */
    List<RuleNameDto> getAllRuleNames();
}
