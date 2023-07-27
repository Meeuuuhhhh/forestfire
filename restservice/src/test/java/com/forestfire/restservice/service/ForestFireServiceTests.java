package com.forestfire.restservice.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Array;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.forestfire.restservice.dto.ForestFireConfigDto;

@SpringBootTest
public class ForestFireServiceTests {
    
    @InjectMocks
    private ForestFireService forestFireService;

    @Test
    void initForestFire_whenSizeIs0_shouldThrowException(){

        int width = 0;
        int length = 0;
        ForestFireConfigDto ffConfig = new ForestFireConfigDto(width, length, 100);
        assertThrows(Exception.class, () -> {
            forestFireService.initForestFire(ffConfig);
        });

    }

    @Test
    void initForestFire_whenConfigIsOk_shouldReturnGoodNumberOfElements(){

        int width = 10;
        int length = 10;
        ForestFireConfigDto ffConfig = new ForestFireConfigDto(width, length, 100);
        String[][] forestFireResult;
        int size = 0;
        try {
            forestFireResult = forestFireService.initForestFire(ffConfig);
            for (int i = 0; i < Array.getLength(forestFireResult); i++) {
                size += Array.getLength(forestFireResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(size == width * length, "Final size is erroneous");
    }

    @Test
    void calculateNextStep_whenConfigIsNull_shouldReturnError(){

        String[][] forestFireData = null;
        ForestFireConfigDto ffConfig = null;
        assertThrows(Exception.class, () -> {
            forestFireService.calculateNextStep(ffConfig, forestFireData);
        });


    }

}
