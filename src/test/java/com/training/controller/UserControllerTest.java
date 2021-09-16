package com.training.controller;

import com.training.entities.User;
import com.training.entities.enumeration.Gender;
import com.training.entities.enumeration.Role;
import com.training.repository.UserRepository;
import com.training.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author ChungCM
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {


    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void showChangePasswordForm() throws Exception {
        User user = new User();
        user.setId(1);
        user.setPassword("11111111");
        user.setAccount("ChungCM");
        user.setEmail("chungpyo.caominh@gmail.com");
        user.setFacebook("fb.com/chungpyo");
        user.setName("Chung");
        user.setNational("VN");
        user.setTelNumber("0123456789");
        user.setGender(Gender.Male);
        user.setRole(Role.ROLE_ADMIN);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userService.findUserById(user.getId())).thenReturn(user);
        mockMvc.perform(get("/change-password/" + user.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void test_submitChangePasswordForm_success() throws Exception {
        User user = new User();
        user.setId(1);
        user.setPassword("11111111");
        user.setAccount("ChungCM");
        user.setEmail("chungpyo.caominh@gmail.com");
        user.setFacebook("fb.com/chungpyo");
        user.setName("Chung");
        user.setNational("VN");
        user.setTelNumber("0123456789");
        user.setGender(Gender.Male);
        //user.setRole(Role.Admin);
        String newPassword = "12345678";
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userService.findUserByAccountAndPassword(user.getAccount(), user.getPassword())).thenReturn(user);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                ((User)arguments[0]).setPassword(newPassword);
                return null;
            }
        }).when(userService).changePassword(user);
        userService.changePassword(user);
        mockMvc.perform(post("/change-password")
                            .param("account", "ChungCM")
                            .param("password", "11111111")
                            .param("newPassword", newPassword))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));






    }
}