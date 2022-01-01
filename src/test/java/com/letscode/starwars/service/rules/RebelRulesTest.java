package com.letscode.starwars.service.rules;

import com.letscode.starwars.base.BaseTest;
import com.letscode.starwars.model.Enuns;
import com.letscode.starwars.model.Enuns.TypeExchange;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.Resource;
import com.letscode.starwars.model.dto.ExchangeResourseDTO;
import com.letscode.starwars.model.dto.ResourceQuantityDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RebelRulesTest extends BaseTest {

    private RebelRules rebelRules;
    private Rebel rebel;
    private List<ResourceQuantityDTO> resourceQuantities;

    @BeforeEach
    private void initializer(){
        rebelRules = new RebelRules();
        rebel = fakeRebel(1L);
        resourceQuantities = List.of(ResourceQuantityDTO.builder()
                .resourceType(Enuns.ResourceType.AGUA)
                .quantity(1)
                .build());
    }

    @Test
    void checkRequiredField_NameIsEmpty(){
        rebel.setName("");
        assertThrows(RuntimeException.class, () -> rebelRules.checkRequiredField(rebel));
    }

    @Test
    void checkRequiredField_AgeLessThenZero(){
        rebel.setAge(null);
        assertThrows(RuntimeException.class, () -> rebelRules.checkRequiredField(rebel));
    }

    @Test
    void applyExchageResourses() {
        Rebel rebelOffer = fakeRebel(1L), rebelRequest = fakeRebel(2L);

        rebelOffer.getResources().clear();
        rebelOffer.getResources().add(Resource.builder().resourceType(Enuns.ResourceType.ARMA).quantity(1).build());

        rebelRequest.getResources().clear();
        rebelRequest.getResources().add(Resource.builder().resourceType(Enuns.ResourceType.AGUA).quantity(2).build());

        ExchangeResourseDTO exchangeResourseDTO = fakeExchangeResourceDTO();

        exchangeResourseDTO.getResourcesOffer().clear();
        exchangeResourseDTO.getResourcesOffer().add(ResourceQuantityDTO.builder().resourceType(Enuns.ResourceType.ARMA).quantity(1).build());

        exchangeResourseDTO.getResourcesRequest().clear();
        exchangeResourseDTO.getResourcesRequest().add(ResourceQuantityDTO.builder().resourceType(Enuns.ResourceType.AGUA).quantity(1).build());

        rebelRules.applyExchageResourses(rebelOffer, rebelRequest, exchangeResourseDTO);

        assertEquals(true,rebelOffer.getResources().get(0).getQuantity() == 0);
        assertEquals(true,rebelRequest.getResources().get(0).getQuantity() == 1);

    }

    @Test
    void relationshipRebelResource() {
    }
}