package com.nnk.springboot.integration;

import com.nnk.springboot.domain.Dto.UserDto;
import com.nnk.springboot.exception.NotFoundException;
import com.nnk.springboot.service.UserService;
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
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserService userService;

    @BeforeEach
    private void addUser() {
        UserDto userDto = UserDto.builder().username("username").password("123456Aa*").fullname("fullname").role("USER").build();
        userService.createUser(userDto);
    }

    @Test
    @DisplayName("GET request (/user/list) must return a list of users")
    public void getUserTest() throws Exception {

        mockMvc.perform(get("/user/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(content().string(containsString("username")));
    }

    @Test
    @DisplayName("GET request (/user/add)")
    public void getUserAddTest() throws Exception {

        mockMvc.perform(get("/user/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeExists("userDto"));
    }

    @Test
    @DisplayName("POST request (/user/validate) must save new user")
    public void postNewUserTest() throws Exception {

        UserDto userDto = UserDto.builder()
                .username("username1")
                .password("123456Aa*")
                .fullname("fullname1")
                .role("USER").build();

        mockMvc.perform(post("/user/validate")
                .param("username", userDto.getUsername())
                .param("password", userDto.getPassword())
                .param("fullname", userDto.getFullname())
                .param("role", userDto.getRole()))
                .andDo(print())
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(status().is3xxRedirection())
                .andReturn().getResponse().containsHeader("username1");
    }

    @Test
    @DisplayName("POST request (/user/validate) with error")
    public void postNewUserWithErrorTest() throws Exception {

        UserDto userDto = UserDto.builder()
                .username("username1")
                .password("123456A*")
                .fullname("fullname1")
                .role("USER").build();

        mockMvc.perform(post("/user/validate")
                .param("username", userDto.getUsername())
                .param("password", userDto.getPassword())
                .param("fullname", userDto.getFullname())
                .param("role", userDto.getRole()))
                .andDo(print())
                .andExpect(view().name("user/add"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("userDto"))
                .andReturn().getResponse().containsHeader("Password must contain 1 or more lowercase characters");
    }

    @Test
    @DisplayName("POST request (/user/validate) with username already exist catch AlreadyExistException")
    public void postNewUserWithAlreadyExistUsernameTest() throws Exception {

        UserDto userDto = UserDto.builder()
                .username("username")
                .password("123456A*")
                .fullname("fullname1")
                .role("USER").build();

        mockMvc.perform(post("/user/validate")
                .param("username", userDto.getUsername())
                .param("password", userDto.getPassword())
                .param("fullname", userDto.getFullname())
                .param("role", userDto.getRole()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userDto"))
                .andReturn().getResponse().containsHeader("Cannot create new user because username : username already exists");
    }

    @Test
    @DisplayName("GET request (/user/update/{id}) with existing id")
    public void getUserUpdateByExistingIdTest() throws Exception {

        mockMvc.perform(get("/user/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("userDto"));
    }

    @Test
    @DisplayName("GET request (/user/update/{id}) with unknown id catch NotFoundException")
    public void getUserUpdateByUnknownIdTest() throws Exception {

        mockMvc.perform(get("/user/update/100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("notFound"));
    }

    @Test
    @DisplayName("POST request (/user/update/{id}) must update a user")
    public void postUserToUpdateTest() throws Exception {

        UserDto userDto = UserDto.builder()
                .username("usernameUpdated")
                .password("123456Aa*Updated")
                .fullname("fullnameUpdate")
                .role("USER").build();

        mockMvc.perform(post("/user/update/1")
                .param("username", userDto.getUsername())
                .param("password", userDto.getPassword())
                .param("fullname", userDto.getFullname())
                .param("role", userDto.getRole()))
                .andDo(print())
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(status().is3xxRedirection())
                .andReturn().getResponse().containsHeader("usernameUpdated");
    }

    @Test
    @DisplayName("POST request (/user/update/{id}) with error")
    public void postUserToUpdateWithErrorTest() throws Exception {

        UserDto userDto = UserDto.builder()
                .username("")
                .password("123456A*")
                .fullname("fullname1")
                .role("USER").build();

        mockMvc.perform(post("/user/update/1")
                .param("username", userDto.getUsername())
                .param("password", userDto.getPassword())
                .param("fullname", userDto.getFullname())
                .param("role", userDto.getRole()))
                .andDo(print())
                .andExpect(view().name("user/update"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("userDto"))
                .andReturn().getResponse().containsHeader("Username is mandatory");
    }

    @Test
    @DisplayName("POST request (/user/update/{id}) with unknown id catch NotFoundException")
    public void postUserToUpdateWithUnknownIdTest() throws Exception {

        UserDto userDto = UserDto.builder()
                .username("username")
                .password("123456Aa*")
                .fullname("fullname1")
                .role("USER").build();

        mockMvc.perform(post("/user/update/100")
                .param("username", userDto.getUsername())
                .param("password", userDto.getPassword())
                .param("fullname", userDto.getFullname())
                .param("role", userDto.getRole()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("notFound"));
    }

    @Test
    @DisplayName("GET request (/user/delete/{id}) delete a user")
    public void getUserDeleteByIdTest() throws Exception {

        mockMvc.perform(get("/user/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));

        assertThrows(NotFoundException.class, () -> userService.getUserById(1));
    }
}

