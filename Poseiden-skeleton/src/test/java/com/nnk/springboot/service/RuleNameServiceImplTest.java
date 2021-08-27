package com.nnk.springboot.service;

import com.nnk.springboot.config.DataSourceTest;
import com.nnk.springboot.domain.Dto.RuleNameDto;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceImplTest {

    @Mock
    RuleNameRepository ruleNameRepository;
    @InjectMocks
    RuleNameServiceImpl ruleNameService;
    @InjectMocks
    DataSourceTest dataSourceTest;

    @BeforeEach
    void init(){
        dataSourceTest.clearRuleNameMocked();
        dataSourceTest.createRuleNameListMocked();
    }

    @Test
    @DisplayName("Create new ruleName")
    void createRuleNameTest() {

        //GIVEN
        RuleNameDto ruleNameDto = RuleNameDto.builder().id(1).name("name4").description("description4").json("json4").template("template4").sqlStr("sqlStr4").sqlPart("sqlPart4").build();

        //WHEN
        ruleNameService.createRuleName(ruleNameDto);

        //THEN
        Mockito.verify(ruleNameRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("Get all rules name")
    void getAllRulesNameTest() {

        //GIVEN
        List<RuleName> ruleNameList = dataSourceTest.getRuleNameListMocked();
        Mockito.when(ruleNameRepository.findAllByOrderByIdDesc()).thenReturn(ruleNameList);

        //THEN
        List<RuleNameDto> ruleNameDtoList = ruleNameService.getAllRuleNames();

        //WHEN
        assertEquals(ruleNameList.size(), ruleNameDtoList.size());
        assertEquals(ruleNameList.get(0).getName(), ruleNameDtoList.get(0).getName());
    }

    @Test
    @DisplayName("Get existing ruleName by id")
    void getExistingRuleNameByIdTest() {

        //GIVEN
        Mockito.when(ruleNameRepository.existsById(1)).thenReturn(true);
        RuleName ruleName = dataSourceTest.getRuleNameListMocked().get(0);
        Mockito.when(ruleNameRepository.getById(1)).thenReturn(ruleName);

        //THEN
        RuleNameDto ruleNameDto = ruleNameService.getRuleNameById(1);

        //WHEN
        assertEquals(ruleName.getName(), ruleNameDto.getName());
    }

    @Test
    @DisplayName("Get unknown ruleName by id throw NotFoundException")
    void getUnknownRuleNameByIdTest() {

        //GIVEN
        Mockito.when(ruleNameRepository.existsById(5)).thenReturn(false);

        //THEN

        //WHEN
        assertThrows(NotFoundException.class, () -> ruleNameService.getRuleNameById(5));
    }

    @Test
    @DisplayName("Update an existing ruleName")
    void updateExistingRuleNameTest() {

        //GIVEN
        Mockito.when(ruleNameRepository.existsById(1)).thenReturn(true);
        RuleName ruleNameToUpdate = dataSourceTest.getRuleNameListMocked().get(0);
        Mockito.when(ruleNameRepository.getById(1)).thenReturn(ruleNameToUpdate);
        RuleNameDto ruleNameDto = RuleNameDto.builder().id(1).name("name1Updated").description("description1Updated").json("json1Updated").template("template1Updated").sqlStr("sqlStr1Updated").sqlPart("sqlPart1Updated").build();

        //THEN
        ruleNameService.updateRuleName(1, ruleNameDto);

        //WHEN
        Mockito.verify(ruleNameRepository, Mockito.times(1)).save(ruleNameToUpdate);
        assertEquals("name1Updated", ruleNameToUpdate.getName());
    }

    @Test
    @DisplayName("Try to update an unknown ruleName throw NotFoundException")
    void updateUnknownRuleNameTest() {

        //GIVEN
        Mockito.when(ruleNameRepository.existsById(5)).thenReturn(false);
        RuleNameDto ruleNameDto = RuleNameDto.builder().id(1).name("name1Updated").description("description1Updated").json("json1Updated").template("template1Updated").sqlStr("sqlStr1Updated").sqlPart("sqlPart1Updated").build();

        //THEN

        //WHEN
        assertThrows(NotFoundException.class, () -> ruleNameService.updateRuleName(5, ruleNameDto));
    }

    @Test
    @DisplayName("Delete a ruleName")
    void deleteRuleNameTest() {

        //GIVEN

        //WHEN
        ruleNameService.deleteRuleName(1);

        //THEN
        Mockito.verify(ruleNameRepository, Mockito.times(1)).deleteById(any());
    }

}
