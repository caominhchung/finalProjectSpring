package com.training.service.impl;

import com.training.entities.Trainer;
import com.training.service.TrainerService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TrainerServiceImplTest {

    @MockBean
    private TrainerService trainerService;

    @Test
    void test_findAll() throws Exception{
        Trainer trainer1 = new Trainer();
        trainer1.setName("trainer1");
        trainer1.setId(1);
        Trainer trainer2 = new Trainer();
        trainer2.setId(2);
        trainer2.setAccount("trainer2");
        trainer2.setPassword("12321");

        Mockito.when(trainerService.findAll()).thenReturn(Arrays.asList(trainer1, trainer2));
        List<Trainer> trainers = trainerService.findAll();
        Assert.assertEquals(2,trainers.size());
        Mockito.verify(trainerService, Mockito.times(1)).findAll();
    }

    @Test
    void test_findById() throws Exception {
        Trainer trainer2 = new Trainer();
        trainer2.setId(2);
        trainer2.setAccount("trainer2");
        trainer2.setPassword("12321");
        Mockito.when(trainerService.findById(2)).thenReturn(trainer2);
        Trainer trainer = trainerService.findById(2);
        Assert.assertEquals(trainer.getId(), trainer2.getId());
        Assert.assertEquals(trainer.getPassword(), trainer2.getPassword());
        Mockito.verify(trainerService, Mockito.times(1)).findById(2);

    }
}