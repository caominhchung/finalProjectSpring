package com.training.controller;

import com.training.entities.User;
import com.training.entities.enumeration.Gender;
import com.training.entities.enumeration.Role;
import com.training.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author tungns14
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_show_login_page_success() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));;


    }

    @Test
    void test_submit_login_success() throws Exception {
        User user = new User();
        user.setId(1);
        user.setPassword("123123123");
        user.setAccount("tungns14");
        user.setEmail("tungns14@email.com");
        user.setFacebook("tungns14FB");
        user.setName("tung");
        user.setNational("VN");
        user.setTelNumber("0123123123");
        user.setGender(Gender.Male);
        user.setRole(Role.ROLE_ADMIN);

        Mockito.when(userService.findUserByAccountAndPassword("tungns14","123123123")).thenReturn(user);
        mockMvc.perform(post("/login")
                        .param("userName","tungns14")
                        .param("password","123123123")
                )
//                .andDo(print())
//                .andExpect(status().isOk())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("dashboard"));

        Mockito.verify(userService,Mockito.times(1)).findUserByAccountAndPassword("tungns14","123123123");
        Mockito.verifyNoMoreInteractions(userService);


    }
}