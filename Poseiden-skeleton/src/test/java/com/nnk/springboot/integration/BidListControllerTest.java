package com.nnk.springboot.integration;

import com.nnk.springboot.domain.Dto.BidListDto;
import com.nnk.springboot.exception.NotFoundException;
import com.nnk.springboot.service.BidListService;
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
public class BidListControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    BidListService bidListService;

    @BeforeEach
    private void addBid() {
        BidListDto bidListDto = BidListDto.builder().account("test").type("test").bidQuantity(10d).build();
        bidListService.createBidList(bidListDto);
    }

    @Test
    @DisplayName("GET request (/bidList/list) must return a list of bid")
    public void getBidListTest() throws Exception {

        mockMvc.perform(get("/bidList/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(content().string(containsString("test")));
    }

    @Test
    @DisplayName("GET request (/bidList/add)")
    public void getBidListAddTest() throws Exception {

        mockMvc.perform(get("/bidList/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"))
                .andExpect(model().attributeExists("bidListDto"));
    }

    @Test
    @DisplayName("POST request (/bidList/validate) must save new bid")
    public void postNewBidTest() throws Exception {

        BidListDto bidListDto = BidListDto.builder()
                .account("accountTest")
                .type("typeTest")
                .bidQuantity(150d)
                .build();

        mockMvc.perform(post("/bidList/validate")
                .param("account", bidListDto.getAccount())
                .param("type", bidListDto.getType())
                .param("bidQuantity", String.valueOf(bidListDto.getBidQuantity())))
                .andDo(print())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(status().is3xxRedirection())
                .andReturn().getResponse().containsHeader("accountTest");
    }

    @Test
    @DisplayName("POST request (/bidList/validate) with error")
    public void postNewBidWithErrorTest() throws Exception {

        BidListDto bidListDto = BidListDto.builder()
                .account("")
                .type("typeTest")
                .bidQuantity(100d)
                .build();

        mockMvc.perform(post("/bidList/validate")
                .param("account", bidListDto.getAccount())
                .param("type", bidListDto.getType())
                .param("bidQuantity", String.valueOf(bidListDto.getBidQuantity())))
                .andDo(print())
                .andExpect(view().name("bidList/add"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("bidListDto"));
    }

    @Test
    @DisplayName("GET request (/bidList/update/{id}) with existing id")
    public void getBidListUpdateByExistingIdTest() throws Exception {

        mockMvc.perform(get("/bidList/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"))
                .andExpect(model().attributeExists("bidListDto"));
    }

    @Test
    @DisplayName("GET request (/bidList/update/{id}) with unknown id catch NotFoundException")
    public void getBidListUpdateByUnknownIdTest() throws Exception {

        mockMvc.perform(get("/bidList/update/100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("notFound"));
    }

    @Test
    @DisplayName("POST request (/bidList/update/{id}) must update a bid")
    public void postBidToUpdateTest() throws Exception {

        BidListDto bidListDto = BidListDto.builder()
                .account("accountTestUpdated")
                .type("typeTestUpdated")
                .bidQuantity(200d)
                .build();

        mockMvc.perform(post("/bidList/update/1")
                .param("account", bidListDto.getAccount())
                .param("type", bidListDto.getType())
                .param("bidQuantity", String.valueOf(bidListDto.getBidQuantity())))
                .andDo(print())
                .andExpect(view().name("redirect:/bidList/list"))
                .andExpect(status().is3xxRedirection())
                .andReturn().getResponse().containsHeader("accountTestUpdated");
    }

    @Test
    @DisplayName("POST request (/bidList/update/{id}) with error")
    public void postBidToUpdateWithErrorTest() throws Exception {

        BidListDto bidListDto = BidListDto.builder()
                .account("")
                .type("typeTestUpdated")
                .bidQuantity(200d)
                .build();

        mockMvc.perform(post("/bidList/update/1")
                .param("account", bidListDto.getAccount())
                .param("type", bidListDto.getType())
                .param("bidQuantity", String.valueOf(bidListDto.getBidQuantity())))
                .andDo(print())
                .andExpect(view().name("bidList/update"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("bidListDto"));
    }

    @Test
    @DisplayName("POST request (/bidList/update/{id}) with unknown id catch NotFoundException")
    public void postBidToUpdateWithUnknownIdTest() throws Exception {

        BidListDto bidListDto = BidListDto.builder()
                .account("accountTestUpdated")
                .type("typeTestUpdated")
                .bidQuantity(200d)
                .build();

        mockMvc.perform(post("/bidList/update/100")
                .param("account", bidListDto.getAccount())
                .param("type", bidListDto.getType())
                .param("bidQuantity", String.valueOf(bidListDto.getBidQuantity())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("notFound"));
    }

    @Test
    @DisplayName("GET request (/bidList/delete/{id}) delete a bid")
    public void getBidDeleteByIdTest() throws Exception {

        mockMvc.perform(get("/bidList/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"));

        assertThrows(NotFoundException.class, () -> bidListService.getBidListById(1));
    }
}
