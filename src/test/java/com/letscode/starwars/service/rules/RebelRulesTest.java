package com.letscode.starwars.service.rules;

import com.letscode.starwars.model.Enuns;
import com.letscode.starwars.model.Enuns.TypeExchange;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.Resource;
import com.letscode.starwars.model.dto.ResourceQuantityDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RebelRulesTest {

    private RebelRules rebelRules;
    private Rebel rebel;
    private List<ResourceQuantityDTO> resourceQuantities;

    @BeforeEach
    private void initializer(){
        rebelRules = new RebelRules();
        rebel = Rebel.builder().name("AnyName").build();
        resourceQuantities = List.of(ResourceQuantityDTO.builder()
                .resourceType(Enuns.ResourceType.AGUA)
                .quantity(1)
                .build());
    }

    @Test
    void checkAvailabiltyResourceWhenIsNotAvailableOffer() {
        TypeExchange type = TypeExchange.OFFER;
        List<Resource> resources = Collections.emptyList();
        rebel.setResources(resources);
        assertThrows(RuntimeException.class, () -> rebelRules.checkAvailabiltyResource(rebel, resourceQuantities, type));
    }

    @Test
    void checkAvailabiltyResourceWhenIsNotAvailableRequested() {
        TypeExchange type = TypeExchange.REQUEST;
        List<Resource> resources = Collections.emptyList();
        rebel.setResources(resources);
        assertThrows(RuntimeException.class, () -> rebelRules.checkAvailabiltyResource(rebel, resourceQuantities, type));
    }

    @Test
    void checkAvailabiltyResourceWhenIsAvailableOffer() {
        List<Resource> resources = List.of(Resource.builder()
                .resourceType(Enuns.ResourceType.AGUA)
                .quantity(1)
                .build());
        rebel.setResources(resources);
        TypeExchange type = TypeExchange.OFFER;
        rebelRules.checkAvailabiltyResource(rebel, resourceQuantities, type);
    }

    @Test
    void checkAvailabiltyResourceWhenIsAvailableRequested() {
        List<Resource> resources = List.of(Resource.builder()
                .resourceType(Enuns.ResourceType.AGUA)
                .quantity(1)
                .build());
        rebel.setResources(resources);
        TypeExchange type = TypeExchange.REQUEST;
        rebelRules.checkAvailabiltyResource(rebel, resourceQuantities, type);
    }

    @Test
    void checkQuantityResource() {
    }

    @Test
    void applyExchageResourses() {
    }

    @Test
    void relationshipRebelResource() {
    }
}