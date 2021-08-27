package com.nnk.springboot.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("GET request (/login) USER with existing user and password return authentication ok ")
    public void userLoginTest() throws Exception {
        mockMvc.perform(formLogin("/login")
                .user("user")
                .password("123456Aa*"))
                .andExpect(authenticated());
    }

    @Test
    @DisplayName("GET request (/login) USER with wrong password return authentication ko")
    public void userLoginFailTest() throws Exception {
        mockMvc.perform(formLogin("/login")
                .user("user")
                .password("wrongPassword"))
                .andExpect(unauthenticated());
    }

    @Test
    @DisplayName("GET request (/login) ADMIN with existing user and password return authentication ok ")
    public void adminLoginTest() throws Exception {
        mockMvc.perform(formLogin("/login")
                .user("admin")
                .password("123456Aa*"))
                .andExpect(authenticated());
    }

    @Test
    @DisplayName("GET request (/login) ADMIN with wrong password return authentication ko")
    public void adminLoginFailTest() throws Exception {
        mockMvc.perform(formLogin("/login")
                .user("admin")
                .password("wrongPassword"))
                .andExpect(unauthenticated());
    }
}
