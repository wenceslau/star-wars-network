package com.letscode.starwars.service;

import com.letscode.starwars.base.BaseTest;
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
import org.mockito.exceptions.verification.TooManyActualInvocations;
import org.mockito.verification.VerificationMode;


class RebelServiceImplTest extends BaseTest {


    private RebelServiceImpl rebelService;

    @Mock
    private RebelRepository rebelRepository;

    @Mock
    private RebelRules rebelRules;


    @BeforeEach
    void initializer(){
        MockitoAnnotations.openMocks(this);

        //Instancia minha classe service com os mocks
        rebelService = new RebelServiceImpl();
        rebelService.setRebelRules(rebelRules);
        rebelService.setRebelRepository(rebelRepository);
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
    void executeExchangeResource_WithRebelOfferIsTraitor() {
        Long code1 = 1L;
        Long code2 = 2L;
        Rebel rebelOffer = fakeRebel(code1);
        rebelOffer.setSuspectBy1(4L);
        rebelOffer.setSuspectBy2(5L);
        rebelOffer.setSuspectBy3(6L);
        Mockito.when(rebelRepository.findById(code1)).thenReturn(Optional.of(rebelOffer));
        //assertThrows(RuntimeException.class, () -> rebelService.executeExchangeResource(code1, code2, fakeExchangeResourceDTO()));
        try {
            rebelService.executeExchangeResource(code1, code2, fakeExchangeResourceDTO());
        } catch (RuntimeException e) {
            assertEquals("Você foi considerado traidor, e não pode negociar recursos", e.getMessage());
        }
    }

    @Test
    void executeExchangeResource_WithRebelRequestIsTraitor() {
        Long code1 = 1L;
        Long code2 = 2L;
        Rebel rebelOffer = fakeRebel(code1);
        Mockito.when(rebelRepository.findById(code1)).thenReturn(Optional.of(rebelOffer));

        Rebel rebelRequest = fakeRebel(code2);
        rebelRequest.setSuspectBy1(4L);
        rebelRequest.setSuspectBy2(5L);
        rebelRequest.setSuspectBy3(6L);
        Mockito.when(rebelRepository.findById(code2)).thenReturn(Optional.of(rebelRequest));

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
    void executeExchangeResource_WithNoEnoughResource() {
    }

    @Test
    void executeExchangeResource_WithEnoughResource() {
    }

    @Test
    void executeExchangeResource_CheckSave() {
        Rebel rebelOffer = fakeRebel(1L);
        rebelOffer.setName("Offer");
        rebelOffer.setCode(1L);
        rebelOffer.getResources().add(Resource.builder().resourceType(Enuns.ResourceType.ARMA).quantity(2).build());

        Rebel rebelRequested = fakeRebel(2L);
        rebelRequested.setName("Request");
        rebelRequested.setCode(2L);
        rebelRequested.getResources().add(Resource.builder().resourceType(Enuns.ResourceType.COMIDA).quantity(2).build());
        Mockito.when(rebelRepository.findById(1L)).thenReturn(Optional.of(rebelOffer));
        Mockito.when(rebelRepository.findById(2L)).thenReturn(Optional.of(rebelRequested));
        rebelService.executeExchangeResource(1L, 2L, fakeExchangeResourceDTO());
        try {
            Mockito.verify(rebelRepository).save(Mockito.any());
            fail("The method save would be called 2 times");
        }catch (TooManyActualInvocations ex){
            Assertions.assertThat(ex.getMessage()).contains("Wanted 1 time").contains("But was 2 times");
        }
    }

    @Test
    void markAsTraitor_reportedTwiceByTheSameRebel() {

        Long reportByCode = 1L, suspectCode = 2L;
        Rebel rebelReported = fakeRebel(reportByCode);
        Rebel rebelSuspect = fakeRebel(suspectCode);

        Mockito.when(rebelRepository.findById(reportByCode)).thenReturn(Optional.of(rebelReported));
        Mockito.when(rebelRepository.findById(suspectCode)).thenReturn(Optional.of(rebelSuspect));

        rebelService.markAsTraitor(reportByCode, suspectCode);

        String actual = "";
        String expected = "Você ja denunciou esse rebelde como traidor, não é necessário uma nova denuncia";
        //assertThrows(RuntimeException.class, () -> rebelService.markAsTraitor(reportByCode, suspectCode));
        try {
            rebelService.markAsTraitor(reportByCode, suspectCode);
        } catch (RuntimeException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);

    }

    @Test
    void markAsTraitor_suspectCheckedAsTraitor() {

        Long reportByCode = 1L, suspectCode = 2L;
        Rebel rebelReported = fakeRebel(reportByCode);
        Rebel rebelSuspect = fakeRebel(suspectCode);

        Mockito.when(rebelRepository.findById(reportByCode)).thenReturn(Optional.of(rebelReported));
        Mockito.when(rebelRepository.findById(suspectCode)).thenReturn(Optional.of(rebelSuspect));

        rebelService.markAsTraitor(reportByCode, suspectCode);

        Mockito.verify(rebelRepository).save(rebelSuspect);

        assertTrue(rebelSuspect.getSuspectBy1().equals(reportByCode)
                || rebelSuspect.getSuspectBy2().equals(reportByCode)
                || rebelSuspect.getSuspectBy3().equals(reportByCode) );

    }
}