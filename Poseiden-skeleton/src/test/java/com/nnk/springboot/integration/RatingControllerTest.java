package com.nnk.springboot.integration;

import com.nnk.springboot.domain.Dto.RatingDto;
import com.nnk.springboot.exception.NotFoundException;
import com.nnk.springboot.service.RatingService;
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
public class RatingControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    RatingService ratingService;

    @BeforeEach
    private void addRating() {
        RatingDto ratingDto = RatingDto.builder().moodysRating("moodysRating").sandPRating("sandPRating").fitchRating("fitchRating").orderNumber(1).build();
        ratingService.createRating(ratingDto);
    }

    @Test
    @DisplayName("GET request (/rating/list) must return a list of rating")
    public void getRatingListTest() throws Exception {

        mockMvc.perform(get("/rating/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(content().string(containsString("moodysRating")));
    }

    @Test
    @DisplayName("GET request (/rating/add)")
    public void getRatingAddTest() throws Exception {

        mockMvc.perform(get("/rating/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"))
                .andExpect(model().attributeExists("ratingDto"));
    }

    @Test
    @DisplayName("POST request (/rating/validate) must save new rating")
    public void postNewRatingTest() throws Exception {

        RatingDto ratingDto = RatingDto.builder()
                .moodysRating("moodysRating2")
                .sandPRating("sandPRating2")
                .fitchRating("fitchRating2")
                .orderNumber(2)
                .build();


        mockMvc.perform(post("/rating/validate")
                .param("moodysRating", ratingDto.getMoodysRating())
                .param("sandPRating", ratingDto.getSandPRating())
                .param("fitchRating", ratingDto.getFitchRating())
                .param("orderNumber", String.valueOf(ratingDto.getOrderNumber())))
                .andDo(print())
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(status().is3xxRedirection())
                .andReturn().getResponse().containsHeader("moodysRating2");
    }

    @Test
    @DisplayName("POST request (/rating/validate) with error")
    public void postNewRatingWithErrorTest() throws Exception {

        RatingDto ratingDto = RatingDto.builder()
                .moodysRating("moodysRating22222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222")
                .sandPRating("sandPRating2")
                .fitchRating("fitchRating2")
                .orderNumber(2)
                .build();

        mockMvc.perform(post("/rating/validate")
                .param("moodysRating", ratingDto.getMoodysRating())
                .param("sandPRating", ratingDto.getSandPRating())
                .param("fitchRating", ratingDto.getFitchRating())
                .param("orderNumber", String.valueOf(ratingDto.getOrderNumber())))
                .andDo(print())
                .andExpect(view().name("rating/add"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("ratingDto"));
    }

    @Test
    @DisplayName("GET request (/rating/update/{id}) with existing id")
    public void getRatingUpdateByExistingIdTest() throws Exception {

        mockMvc.perform(get("/rating/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andExpect(model().attributeExists("ratingDto"));
    }

    @Test
    @DisplayName("GET request (/rating/update/{id}) with unknown id catch NotFoundException")
    public void getRatingUpdateByUnknownIdTest() throws Exception {

        mockMvc.perform(get("/rating/update/100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("notFound"));
    }

    @Test
    @DisplayName("POST request (/rating/update/{id}) must update a rating")
    public void postRatingToUpdateTest() throws Exception {

        RatingDto ratingDto = RatingDto.builder()
                .moodysRating("moodysRatingUpdated")
                .sandPRating("sandPRatingUpdated")
                .fitchRating("fitchRatingUpdated")
                .orderNumber(2)
                .build();

        mockMvc.perform(post("/rating/update/1")
                .param("moodysRating", ratingDto.getMoodysRating())
                .param("sandPRating", ratingDto.getSandPRating())
                .param("fitchRating", ratingDto.getFitchRating())
                .param("orderNumber", String.valueOf(ratingDto.getOrderNumber())))
                .andDo(print())
                .andExpect(view().name("redirect:/rating/list"))
                .andExpect(status().is3xxRedirection())
                .andReturn().getResponse().containsHeader("moodysRatingUpdated");
    }

    @Test
    @DisplayName("POST request (/rating/update/{id}) with error")
    public void postRatingToUpdateWithErrorTest() throws Exception {

        RatingDto ratingDto = RatingDto.builder()
                .moodysRating("moodysRating22222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222")
                .sandPRating("sandPRating2")
                .fitchRating("fitchRating2")
                .orderNumber(2)
                .build();

        mockMvc.perform(post("/rating/update/1")
                .param("moodysRating", ratingDto.getMoodysRating())
                .param("sandPRating", ratingDto.getSandPRating())
                .param("fitchRating", ratingDto.getFitchRating())
                .param("orderNumber", String.valueOf(ratingDto.getOrderNumber())))
                .andDo(print())
                .andExpect(view().name("rating/update"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("ratingDto"));
    }

    @Test
    @DisplayName("POST request (/rating/update/{id}) with unknown id catch NotFoundException")
    public void postRatingToUpdateWithUnknownIdTest() throws Exception {

        RatingDto ratingDto = RatingDto.builder()
                .moodysRating("moodysRatingUpdated")
                .sandPRating("sandPRatingUpdated")
                .fitchRating("fitchRatingUpdated")
                .orderNumber(2)
                .build();

        mockMvc.perform(post("/rating/update/100")
                .param("moodysRating", ratingDto.getMoodysRating())
                .param("sandPRating", ratingDto.getSandPRating())
                .param("fitchRating", ratingDto.getFitchRating())
                .param("orderNumber", String.valueOf(ratingDto.getOrderNumber())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("notFound"));
    }

    @Test
    @DisplayName("GET request (/rating/delete/{id}) delete a rating")
    public void getRatingDeleteByIdTest() throws Exception {

        mockMvc.perform(get("/rating/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/rating/list"));

        assertThrows(NotFoundException.class, () -> ratingService.getRatingById(1));
    }
}

