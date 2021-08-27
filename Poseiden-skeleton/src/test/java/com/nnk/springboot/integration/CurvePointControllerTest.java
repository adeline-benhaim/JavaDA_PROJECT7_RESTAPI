package com.nnk.springboot.integration;

import com.nnk.springboot.domain.Dto.CurvePointDto;
import com.nnk.springboot.exception.NotFoundException;
import com.nnk.springboot.service.CurvePointService;
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
public class CurvePointControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    CurvePointService curvePointService;

    @BeforeEach
    private void addCurvePoint() {
        CurvePointDto curvePointDto = CurvePointDto.builder().curveId(1).term(1d).value(1d).build();
        curvePointService.createCurvePoint(curvePointDto);
    }

    @Test
    @DisplayName("GET request (/curvePoint/list) must return a list of curvePoint")
    public void getCurvePointListTest() throws Exception {

        mockMvc.perform(get("/curvePoint/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(content().string(containsString("1")));
    }

    @Test
    @DisplayName("GET request (/curvePoint/add)")
    public void getCurvePointAddTest() throws Exception {

        mockMvc.perform(get("/curvePoint/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(model().attributeExists("curvePointDto"));
    }

    @Test
    @DisplayName("POST request (/curvePoint/validate) must save new curvePoint")
    public void postNewCurvePointTest() throws Exception {

        CurvePointDto curvePointDto = CurvePointDto.builder()
                .curveId(2)
                .term(2d)
                .value(2d)
                .build();

        mockMvc.perform(post("/curvePoint/validate")
                .param("curveId", String.valueOf(curvePointDto.getCurveId()))
                .param("term", String.valueOf(curvePointDto.getTerm()))
                .param("value", String.valueOf(curvePointDto.getValue())))
                .andDo(print())
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(status().is3xxRedirection())
                .andReturn().getResponse().containsHeader("2");
    }

    @Test
    @DisplayName("POST request (/curvePoint/validate) with error")
    public void postNewCurvePointWithErrorTest() throws Exception {

        CurvePointDto curvePointDto = CurvePointDto.builder()
                .term(2d)
                .value(2d)
                .build();

        mockMvc.perform(post("/curvePoint/validate")
                .param("term", String.valueOf(curvePointDto.getTerm()))
                .param("value", String.valueOf(curvePointDto.getValue())))
                .andDo(print())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("curvePointDto"));
    }

    @Test
    @DisplayName("GET request (/curvePoint/update/{id}) with existing id")
    public void getCurvePointUpdateByExistingIdTest() throws Exception {

        mockMvc.perform(get("/curvePoint/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePointDto"));
    }

    @Test
    @DisplayName("GET request (/curvePoint/update/{id}) with unknown id catch NotFoundException")
    public void getCurvePointUpdateByUnknownIdTest() throws Exception {

        mockMvc.perform(get("/curvePoint/update/100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("notFound"));
    }

    @Test
    @DisplayName("POST request (/curvePoint/update/{id}) must update a curvePoint")
    public void postCurvePointToUpdateTest() throws Exception {

        CurvePointDto curvePointDto = CurvePointDto.builder()
                .curveId(20)
                .term(20d)
                .value(20d)
                .build();

        mockMvc.perform(post("/curvePoint/update/1")
                .param("curveId", String.valueOf(curvePointDto.getCurveId()))
                .param("term", String.valueOf(curvePointDto.getTerm()))
                .param("value", String.valueOf(curvePointDto.getValue())))
                .andDo(print())
                .andExpect(view().name("redirect:/curvePoint/list"))
                .andExpect(status().is3xxRedirection())
                .andReturn().getResponse().containsHeader("20");
    }

    @Test
    @DisplayName("POST request (/curvePoint/update/{id}) with error")
    public void postCurvePointToUpdateWithErrorTest() throws Exception {

        CurvePointDto curvePointDto = CurvePointDto.builder()
                .term(20d)
                .value(20d)
                .build();

        mockMvc.perform(post("/curvePoint/update/1")
                .param("term", String.valueOf(curvePointDto.getTerm()))
                .param("value", String.valueOf(curvePointDto.getValue())))
                .andDo(print())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("curvePointDto"));
    }

    @Test
    @DisplayName("POST request (/curvePoint/update/{id}) with unknown id catch NotFoundException")
    public void postCurvePointToUpdateWithUnknownIdTest() throws Exception {

        CurvePointDto curvePointDto = CurvePointDto.builder()
                .curveId(20)
                .term(20d)
                .value(20d)
                .build();

        mockMvc.perform(post("/curvePoint/update/100")
                .param("curveId", String.valueOf(curvePointDto.getCurveId()))
                .param("term", String.valueOf(curvePointDto.getTerm()))
                .param("value", String.valueOf(curvePointDto.getValue())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("notFound"));
    }

    @Test
    @DisplayName("GET request (/curvePoint/delete/{id}) delete a curvePoint")
    public void getCurvePointDeleteByIdTest() throws Exception {

        mockMvc.perform(get("/curvePoint/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/curvePoint/list"));

        assertThrows(NotFoundException.class, () -> curvePointService.getCurvePointById(1));
    }
}
