package com.nnk.springboot.integration;

import com.nnk.springboot.domain.Dto.RuleNameDto;
import com.nnk.springboot.exception.NotFoundException;
import com.nnk.springboot.service.RuleNameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@WithMockUser(username = "user", password = "123456Aa*", roles = "USER")
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RuleNameControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    RuleNameService ruleNameService;

    @BeforeEach
    private void addRuleName() {
        RuleNameDto ruleNameDto = RuleNameDto.builder().name("name").description("description").json("json").template("template").sqlStr("sqlStr").sqlPart("sqlPart").build();
        ruleNameService.createRuleName(ruleNameDto);
    }

    @Test
    @DisplayName("GET request (/ruleName/list) must return a list of ruleName")
    public void getRuleNameListTest() throws Exception {

        mockMvc.perform(get("/ruleName/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(content().string(containsString("name")));
    }

    @Test
    @DisplayName("GET request (/ruleName/add)")
    public void getRuleNameAddTest() throws Exception {

        mockMvc.perform(get("/ruleName/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"))
                .andExpect(model().attributeExists("ruleNameDto"));
    }

    @Test
    @DisplayName("POST request (/ruleName/validate) must save new ruleName")
    public void postNewRuleNameTest() throws Exception {

        RuleNameDto ruleNameDto = RuleNameDto.builder()
                .name("name1")
                .description("description1")
                .json("json").template("template1")
                .sqlStr("sqlStr").sqlPart("sqlPart1")
                .build();

        mockMvc.perform(post("/ruleName/validate")
                .param("name", ruleNameDto.getName())
                .param("description", ruleNameDto.getDescription())
                .param("json", ruleNameDto.getJson())
                .param("sqlStr", ruleNameDto.getSqlStr()))
                .andDo(print())
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(status().is3xxRedirection())
                .andReturn().getResponse().containsHeader("name1");
    }

    @Test
    @DisplayName("POST request (/ruleName/validate) with error")
    public void postNewRuleNameWithErrorTest() throws Exception {

        RuleNameDto ruleNameDto = RuleNameDto.builder()
                //@Size(max = 125, message = "125 characters maximum allowed")
                .name("name111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111")
                .description("description1")
                .json("json").template("template1")
                .sqlStr("sqlStr").sqlPart("sqlPart1")
                .build();

        mockMvc.perform(post("/ruleName/validate")
                .param("name", ruleNameDto.getName())
                .param("description", ruleNameDto.getDescription())
                .param("json", ruleNameDto.getJson())
                .param("sqlStr", ruleNameDto.getSqlStr()))
                .andDo(print())
                .andExpect(view().name("ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("ruleNameDto"))
                .andReturn().getResponse().containsHeader("125 characters maximum allowed");
    }

    @Test
    @DisplayName("GET request (/ruleName/update/{id}) with existing id")
    public void getRuleNameUpdateByExistingIdTest() throws Exception {

        mockMvc.perform(get("/ruleName/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andExpect(model().attributeExists("ruleNameDto"));
    }

    @Test
    @DisplayName("GET request (/ruleName/update/{id}) with unknown id catch NotFoundException")
    public void getRuleNameUpdateByUnknownIdTest() throws Exception {

        mockMvc.perform(get("/ruleName/update/100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("notFound"));
    }

    @Test
    @DisplayName("POST request (/ruleName/update/{id}) must update a ruleName")
    public void postRuleNameToUpdateTest() throws Exception {

        RuleNameDto ruleNameDto = RuleNameDto.builder()
                .name("nameUpdated")
                .description("descriptionUpdated")
                .json("json").template("templateUpdated")
                .sqlStr("sqlStr").sqlPart("sqlPartUpdated")
                .build();

        mockMvc.perform(post("/ruleName/update/1")
                .param("name", ruleNameDto.getName())
                .param("description", ruleNameDto.getDescription())
                .param("json", ruleNameDto.getJson())
                .param("sqlStr", ruleNameDto.getSqlStr()))
                .andDo(print())
                .andExpect(view().name("redirect:/ruleName/list"))
                .andExpect(status().is3xxRedirection())
                .andReturn().getResponse().containsHeader("nameUpdated");
    }

    @Test
    @DisplayName("POST request (/ruleName/update/{id}) with error")
    public void postRuleNameToUpdateWithErrorTest() throws Exception {

        RuleNameDto ruleNameDto = RuleNameDto.builder()
                //@Size(max = 125, message = "125 characters maximum allowed")
                .name("name111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111")
                .description("description1")
                .json("json").template("template1")
                .sqlStr("sqlStr").sqlPart("sqlPart1")
                .build();

        mockMvc.perform(post("/ruleName/update/1")
                .param("name", ruleNameDto.getName())
                .param("description", ruleNameDto.getDescription())
                .param("json", ruleNameDto.getJson())
                .param("sqlStr", ruleNameDto.getSqlStr()))
                .andDo(print())
                .andExpect(view().name("ruleName/update"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("ruleNameDto"));
    }

    @Test
    @DisplayName("POST request (/ruleName/update/{id}) with unknown id catch NotFoundException")
    public void postRuleNameToUpdateWithUnknownIdTest() throws Exception {

        RuleNameDto ruleNameDto = RuleNameDto.builder()
                .name("nameUpdated")
                .description("descriptionUpdated")
                .json("json").template("templateUpdated")
                .sqlStr("sqlStr").sqlPart("sqlPartUpdated")
                .build();

        mockMvc.perform(post("/ruleName/update/100")
                .param("name", ruleNameDto.getName())
                .param("description", ruleNameDto.getDescription())
                .param("json", ruleNameDto.getJson())
                .param("sqlStr", ruleNameDto.getSqlStr()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("notFound"));
    }

    @Test
    @DisplayName("GET request (/ruleName/delete/{id}) delete a ruleName")
    public void getRuleNameDeleteByIdTest() throws Exception {

        mockMvc.perform(get("/ruleName/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ruleName/list"));

        assertThrows(NotFoundException.class, () -> ruleNameService.getRuleNameById(1));
    }
}

