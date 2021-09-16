package com.training.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest

public class SmokerTest {

    @Autowired
    private LoginController loginController;


    @Test
    public void contextLoads()  {
        assertThat(loginController).isNotNull();
    }

//    @Test
//    void submitLogin() {
//    }
}