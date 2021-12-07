package com.letscode.starwars.service;

import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.service.interfaces.RebelService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RebelServiceImplTest {

    @Test
    void rebelIsAnTraitor() {
        RebelService rebelService = new RebelServiceImpl();
        var rebel = Rebel.builder().suspectBy1(1L).suspectBy2(2L).suspectBy3(3L).build();
        boolean isTraitor = rebelService.isTraitor(rebel);
        assertEquals(true, isTraitor);
    }

    @Test
    void rebelIsNotAnTraitor() {
        RebelService rebelService = new RebelServiceImpl();
        var rebel = Rebel.builder().suspectBy1(1L).suspectBy2(2L).suspectBy3(null).build();
        boolean isTraitor = rebelService.isTraitor(rebel);
        assertEquals(false, isTraitor);
    }

    @Test
    void executeExchangeResourceWithRebelTraitor() {
    }

    @Test
    void executeExchangeResourceWithNoEnoughResource() {
    }

    @Test
    void executeExchangeResourceWithEnoughResource() {
    }
}