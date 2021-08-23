package com.nnk.springboot.service;

import com.nnk.springboot.Mapper.MapperDto;
import com.nnk.springboot.domain.Dto.RuleNameDto;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
