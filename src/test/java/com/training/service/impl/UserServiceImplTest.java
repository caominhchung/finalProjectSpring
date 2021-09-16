package com.training.service.impl;

import com.training.entities.User;
import com.training.entities.enumeration.Gender;
import com.training.entities.enumeration.Role;
import com.training.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.doAnswer;

/**
 * @author ChungCM
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserServiceImpl.class)
public class UserServiceImplTest {

    @MockBean
    private UserService userService;


    @Before
    public void setUp() throws Exception {


    }

    @Test
    public void changePassword() {
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

        String newPassword = "00000000";


        doAnswer(invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            ((User)arguments[0]).setPassword(newPassword);
            return null;
        }).when(userService).changePassword(user);
        userService.changePassword(user);
        Assert.assertEquals(newPassword, user.getPassword());
        Mockito.verify(userService, Mockito.times(1)).changePassword(user);
    }

    @Test
    public void findUserById() {
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

        Mockito.when(userService.findUserById(1)).thenReturn(user);
        Assert.assertEquals(userService.findUserById(1), user);
        Mockito.verify(userService, Mockito.times(1)).findUserById(1);
    }

    @Test
    public void findUserByAccount() {
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

        String account = "ChungCM";
        Mockito.when(userService.findUserByAccount(account)).thenReturn(user);
        Assert.assertEquals(userService.findUserByAccount(account), user);
        Mockito.verify(userService, Mockito.times(1)).findUserByAccount(account);
    }

    @Test
    public void findUserByAccountAndPassword() {
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

        String account = "ChungCM";
        String password = "11111111";
        Mockito.when(userService.findUserByAccountAndPassword(account, password)).thenReturn(user);
        Assert.assertEquals(userService.findUserByAccountAndPassword(account, password), user);
        Mockito.verify(userService, Mockito.times(1)).findUserByAccountAndPassword(account, password);
    }

    @Test
    public void findUserByTelPhone() {
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

        String phone = "0123456789";
        Mockito.when(userService.findUserByTelPhone(phone)).thenReturn(user);
        Assert.assertEquals(userService.findUserByTelPhone(phone), user);
        Mockito.verify(userService, Mockito.times(1)).findUserByTelPhone(phone);
    }

    @Test
    public void findUserByFacebook() {
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

        String fBook = "fb.com/chungpyo";
        Mockito.when(userService.findUserByFacebook(fBook)).thenReturn(user);
        Assert.assertEquals(userService.findUserByFacebook(fBook), user);
        Mockito.verify(userService, Mockito.times(1)).findUserByFacebook(fBook);
    }

    @Test
    public void findUserByEmail() {
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

        String email = "chungpyo.caominh@gmail.com";
        Mockito.when(userService.findUserByEmail(email)).thenReturn(user);
        Assert.assertEquals(userService.findUserByEmail(email), user);
        Mockito.verify(userService, Mockito.times(1)).findUserByEmail(email);
    }
}