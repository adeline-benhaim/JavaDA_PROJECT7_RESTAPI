package com.nnk.springboot.service;

import com.nnk.springboot.mapper.MapperDto;
import com.nnk.springboot.domain.Dto.RuleNameDto;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RuleNameServiceImpl implements RuleNameService {
    private static final Logger logger = LoggerFactory.getLogger(RuleNameServiceImpl.class);

    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**Create a new rule name
     *
     * @param ruleNameDto new rule name to save
     * @return rule name saved
     */
    @Override
    public RuleName createRuleName(RuleNameDto ruleNameDto) {
        logger.info("Create a new ruleName");
        RuleName newRuleName = RuleName.builder()
                .name(ruleNameDto.getName())
                .description(ruleNameDto.getDescription())
                .json(ruleNameDto.getJson())
                .template(ruleNameDto.getTemplate())
                .sqlStr(ruleNameDto.getSqlStr())
                .sqlPart(ruleNameDto.getSqlPart())
                .build();
        return ruleNameRepository.save(newRuleName);
    }

    /**
     * Get all rules
     *
     * @return a list of all rules
     */
    @Override
    public List<RuleNameDto> getAllRuleNames() {
        logger.info("Get all ruleNames");
        List<RuleName> ruleNameList = ruleNameRepository.findAllByOrderByIdDesc();
        return ruleNameList
                .stream().map(MapperDto::convertToRuleNameDto)
                .collect(Collectors.toList());
    }

    /**
     * Get a rule
     *
     * @param id of the requested rule
     * @return rule found by id
     */
    @Override
    public RuleNameDto getRuleNameById(Integer id) throws NotFoundException {
        logger.info("Get a rule by ID");
        if (!ruleNameRepository.existsById(id)) {
            logger.error("Unable to get rule by id : " + id + " because doesn't exist");
            throw new NotFoundException("Rule id doesn't exist");
        }
        RuleName ruleName = ruleNameRepository.findRuleNameById(id);
        return MapperDto.convertToRuleNameDto(ruleName);
    }

    /**
     * Update a rule
     *
     * @param id rule id to update
     * @param ruleNameDto rule information to update
     * @return rule updated
     */
    @Override
    @Transactional
    public RuleName updateRuleName(Integer id, RuleNameDto ruleNameDto) throws NotFoundException {
        logger.info("Get rule by id");
        if (!ruleNameRepository.existsById(id)) {
            logger.error("Unable to get rule by id : " + id + " because doesn't exist");
            throw new NotFoundException("Rule id doesn't exist");
        }
        RuleName ruleNameToUpdate = ruleNameRepository.findRuleNameById(id);
        ruleNameToUpdate.setName(ruleNameDto.getName());
        ruleNameToUpdate.setDescription(ruleNameDto.getDescription());
        ruleNameToUpdate.setJson(ruleNameDto.getJson());
        ruleNameToUpdate.setTemplate(ruleNameDto.getTemplate());
        ruleNameToUpdate.setSqlStr(ruleNameDto.getSqlStr());
        ruleNameToUpdate.setSqlPart(ruleNameDto.getSqlPart());
        logger.info("Rule updated");
        return ruleNameRepository.save(ruleNameToUpdate);
    }

    /**
     * Delete a rule by id
     *
     * @param id ID of rule to delete
     */
    @Override
    @Transactional
    public void deleteRuleName(Integer id) {
        logger.info("Delete rule : " + id);
        ruleNameRepository.deleteById(id);
    }

}
