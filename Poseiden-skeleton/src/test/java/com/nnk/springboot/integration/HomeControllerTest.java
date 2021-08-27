package com.nnk.springboot.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("GET request (/) must return home page")
    public void getHomePageTest() throws Exception {

        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("GET request (/admin/home) with role ADMIN must return bidList page")
    public void getAdminHomePageWithAdminRoleTest() throws Exception {

        mockMvc.perform(formLogin("/login")
                .user("admin")
                .password("123456Aa*"));

        mockMvc.perform(get("/admin/home"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("GET request (/admin/home) with role USER must return HTTP 403")
    public void getAdminHomePageWithUserRoleTest() throws Exception {

        mockMvc.perform(formLogin("/login")
                .user("user")
                .password("123456Aa*"));

        mockMvc.perform(get("/admin/home"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void userLoginTest() throws Exception {
        mockMvc.perform(formLogin("/login")
                .user("user")
                .password("123456Aa*"))
                .andExpect(authenticated());
    }

    @Test
    public void userLoginFailTest() throws Exception {
        mockMvc.perform(formLogin("/login")
                .user("user")
                .password("wrongPassword"))
                .andExpect(unauthenticated());
    }

    @Test
    public void adminLoginTest() throws Exception {
        mockMvc.perform(formLogin("/login")
                .user("admin")
                .password("123456Aa*"))
                .andExpect(authenticated());
    }

    @Test
    public void adminLoginFailTest() throws Exception {
        mockMvc.perform(formLogin("/login")
                .user("admin")
                .password("wrongPassword"))
                .andExpect(unauthenticated());
    }

}

