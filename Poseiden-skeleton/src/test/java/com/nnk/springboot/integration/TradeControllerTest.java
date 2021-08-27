package com.nnk.springboot.integration;

import com.nnk.springboot.domain.Dto.TradeDto;
import com.nnk.springboot.exception.NotFoundException;
import com.nnk.springboot.service.TradeService;
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
public class TradeControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    TradeService tradeService;

    @BeforeEach
    private void addTrade() {
        TradeDto tradeDto = TradeDto.builder().account("account").type("type").buyQuantity(1d).build();
        tradeService.createTrade(tradeDto);
    }

    @Test
    @DisplayName("GET request (/trade/list) must return a list of trades")
    public void getTradeListTest() throws Exception {

        mockMvc.perform(get("/trade/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(content().string(containsString("account")));
    }

    @Test
    @DisplayName("GET request (/trade/add)")
    public void getTradeAddTest() throws Exception {

        mockMvc.perform(get("/trade/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"))
                .andExpect(model().attributeExists("tradeDto"));
    }

    @Test
    @DisplayName("POST request (/trade/validate) must save new trade")
    public void postNewTradeTest() throws Exception {

        TradeDto tradeDto = TradeDto.builder()
                .account("account1")
                .type("type1")
                .buyQuantity(10d).build();


        mockMvc.perform(post("/trade/validate")
                .param("account", tradeDto.getAccount())
                .param("type", tradeDto.getType())
                .param("buyQuantity", String.valueOf(tradeDto.getBuyQuantity())))
                .andDo(print())
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(status().is3xxRedirection())
                .andReturn().getResponse().containsHeader("account1");
    }

    @Test
    @DisplayName("POST request (/trade/validate) with error")
    public void postNewTradeWithErrorTest() throws Exception {

        TradeDto tradeDto = TradeDto.builder()
                //@Size(max = 30, message = "30 characters maximum allowed")
                .account("account1111111111111111111111111")
                .type("type1")
                .buyQuantity(10d).build();

        mockMvc.perform(post("/trade/validate")
                .param("account", tradeDto.getAccount())
                .param("type", tradeDto.getType())
                .param("buyQuantity", String.valueOf(tradeDto.getBuyQuantity())))
                .andDo(print())
                .andExpect(view().name("trade/add"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("tradeDto"))
                .andReturn().getResponse().containsHeader("30 characters maximum allowed");
    }

    @Test
    @DisplayName("GET request (/trade/update/{id}) with existing id")
    public void getTradeUpdateByExistingIdTest() throws Exception {

        mockMvc.perform(get("/trade/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andExpect(model().attributeExists("tradeDto"));
    }

    @Test
    @DisplayName("GET request (/trade/update/{id}) with unknown id catch NotFoundException")
    public void getTradeUpdateByUnknownIdTest() throws Exception {

        mockMvc.perform(get("/trade/update/100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("notFound"));
    }

    @Test
    @DisplayName("POST request (/trade/update/{id}) must update a trade")
    public void postTradeToUpdateTest() throws Exception {

        TradeDto tradeDto = TradeDto.builder()
                .account("accountUpdated")
                .type("typeUpdated")
                .buyQuantity(100d).build();

        mockMvc.perform(post("/trade/update/1")
                .param("account", tradeDto.getAccount())
                .param("type", tradeDto.getType())
                .param("buyQuantity", String.valueOf(tradeDto.getBuyQuantity())))
                .andDo(print())
                .andExpect(view().name("redirect:/trade/list"))
                .andExpect(status().is3xxRedirection())
                .andReturn().getResponse().containsHeader("accountUpdated");
    }

    @Test
    @DisplayName("POST request (/trade/update/{id}) with error")
    public void postTradeToUpdateWithErrorTest() throws Exception {

        TradeDto tradeDto = TradeDto.builder()
                //@Size(max = 30, message = "30 characters maximum allowed")
                .account("account1111111111111111111111111")
                .type("type1")
                .buyQuantity(10d).build();

        mockMvc.perform(post("/trade/update/1")
                .param("account", tradeDto.getAccount())
                .param("type", tradeDto.getType())
                .param("buyQuantity", String.valueOf(tradeDto.getBuyQuantity())))
                .andDo(print())
                .andExpect(view().name("trade/update"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("tradeDto"));
    }

    @Test
    @DisplayName("POST request (/trade/update/{id}) with unknown id catch NotFoundException")
    public void postTradeToUpdateWithUnknownIdTest() throws Exception {

        TradeDto tradeDto = TradeDto.builder()
                .account("accountUpdated")
                .type("typeUpdated")
                .buyQuantity(100d).build();

        mockMvc.perform(post("/trade/update/100")
                .param("account", tradeDto.getAccount())
                .param("type", tradeDto.getType())
                .param("buyQuantity", String.valueOf(tradeDto.getBuyQuantity())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("notFound"));
    }

    @Test
    @DisplayName("GET request (/trade/delete/{id}) delete a trade")
    public void getTradeDeleteByIdTest() throws Exception {

        mockMvc.perform(get("/trade/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/trade/list"));

        assertThrows(NotFoundException.class, () -> tradeService.getTradeById(1));
    }
}

