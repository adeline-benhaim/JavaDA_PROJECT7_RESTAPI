package com.nnk.springboot.service;

import com.nnk.springboot.domain.Dto.RuleNameDto;
import com.nnk.springboot.domain.RuleName;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * Get a rule
     *
     * @param id of the requested rule
     * @return rule found by id
     */
    RuleNameDto getRuleNameById(Integer id);

    /**
     * Update a rule
     *
     * @param id rule id to update
     * @param ruleNameDto rule information to update
     * @return rule updated
     */
    RuleName updateRuleName(Integer id, RuleNameDto ruleNameDto);

    /**
     * Delete a rule by id
     *
     * @param id ID of rule to delete
     */
    void deleteRuleName(Integer id);
}
