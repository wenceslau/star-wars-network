package com.letscode.starwars.service;

import com.letscode.starwars.model.Enuns;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.Resource;
import com.letscode.starwars.model.dto.ExchangeResourseDTO;
import com.letscode.starwars.model.dto.ResourceQuantityDTO;
import com.letscode.starwars.model.embedded.Localization;
import com.letscode.starwars.repository.RebelRepository;
import com.letscode.starwars.service.interfaces.RebelService;
import com.letscode.starwars.service.rules.RebelRules;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.assertj.core.api.Assertions;


class RebelServiceImplTest {


    private RebelService rebelService;

    @Mock
    private RebelRepository rebelRepository;

    @Mock
    private RebelRules rebelRules;

    @BeforeEach
    void initializer(){
        MockitoAnnotations.openMocks(this);
        //Instancia minha classe service com os mocks
        rebelService = new RebelServiceImpl(rebelRepository, rebelRules);
    }

    @Test
    void rebelIsAnTraitor() {
        var rebel = Rebel.builder().suspectBy1(1L).suspectBy2(2L).suspectBy3(3L).build();
        boolean isTraitor = rebelService.isTraitor(rebel);
        assertEquals(true, isTraitor);
    }

    @Test
    void rebelIsNotAnTraitor() {
        var rebel = Rebel.builder().suspectBy1(1L).suspectBy2(2L).suspectBy3(null).build();
        boolean isTraitor = rebelService.isTraitor(rebel);
        assertEquals(false, isTraitor);
    }

    @Test
    void executeExchangeResourceWithRebelOfferIsTraitor() {
        Long code1 = 1L;
        Long code2 = 2L;
        Rebel rebelOffer = fakeRebel();
        rebelOffer.setSuspectBy1(4L);
        rebelOffer.setSuspectBy2(5L);
        rebelOffer.setSuspectBy3(6L);
        Mockito.when(rebelRepository.findById(1L)).thenReturn(Optional.of(rebelOffer));
        //assertThrows(RuntimeException.class, () -> rebelService.executeExchangeResource(code1, code2, fakeExchangeResourceDTO()));
        try {
            rebelService.executeExchangeResource(code1, code2, fakeExchangeResourceDTO());
        } catch (RuntimeException e) {
            assertEquals("Você foi considerado traidor, e não pode negociar recursos", e.getMessage());
        }
    }

    @Test
    void executeExchangeResourceWithRebelRequestIsTraitor() {
        Long code1 = 1L;
        Long code2 = 2L;
        Rebel rebelOffer = fakeRebel();
        Mockito.when(rebelRepository.findById(1L)).thenReturn(Optional.of(rebelOffer));

        Rebel rebelRequest = fakeRebel();
        rebelRequest.setSuspectBy1(4L);
        rebelRequest.setSuspectBy2(5L);
        rebelRequest.setSuspectBy3(6L);
        Mockito.when(rebelRepository.findById(2L)).thenReturn(Optional.of(rebelRequest));

        String actual = "";
        String expected = "O rebelde "+rebelRequest.getName()+" foi considerado traidor, e não pode negociar recursos";
        //assertThrows(RuntimeException.class, () -> rebelService.executeExchangeResource(code1, code2, fakeExchangeResourceDTO()));
        try {
            rebelService.executeExchangeResource(code1, code2, fakeExchangeResourceDTO());
        } catch (RuntimeException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    void executeExchangeResourceWithNoEnoughResource() {
    }

    @Test
    void executeExchangeResourceWithEnoughResource() {
    }

    private Rebel fakeRebel(){
        var resources = new ArrayList<Resource>();
        resources.add(Resource.builder()
                .resourceType(Enuns.ResourceType.AGUA)
                .quantity(10)
                .build());
        return Rebel.builder()
                .resources(resources)
                .name("AnyName")
                .Localization(Localization.builder()
                        .longitude("99999")
                        .latitude("888888")
                        .galaxy("AnyGalaxy")
                        .build())
                .gender(Enuns.Gender.MASCULINO)
                .age(20)
                .build();
    }

    private ExchangeResourseDTO fakeExchangeResourceDTO() {
        var resourcesOffer = new ArrayList<ResourceQuantityDTO>();
        var resourcesRequest = new ArrayList<ResourceQuantityDTO>();

        resourcesOffer.add(ResourceQuantityDTO.builder().resourceType(Enuns.ResourceType.ARMA).quantity(1).build());
        resourcesRequest.add(ResourceQuantityDTO.builder().resourceType(Enuns.ResourceType.COMIDA).quantity(1).build());

        return ExchangeResourseDTO.builder()
                .resourcesOffer(resourcesOffer)
                .resourcesRequest(resourcesRequest)
                .build();
    }
}