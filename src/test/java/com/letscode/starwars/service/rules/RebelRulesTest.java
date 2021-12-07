package com.letscode.starwars.service.rules;

import com.letscode.starwars.model.Enuns;
import com.letscode.starwars.model.Enuns.TypeExchange;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.Resource;
import com.letscode.starwars.model.dto.ResourceQuantityDTO;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RebelRulesTest {

    @Test
    void checkAvailabiltyResourceWhenIsNotAvailableOffer() {
        RebelRules rebelRules = new RebelRules();

        List<Resource> resources = Collections.emptyList();
        Rebel rebel = Rebel.builder().name("AnyName").resources(resources).build();
        List<ResourceQuantityDTO> resourceQuantities = List.of(ResourceQuantityDTO.builder()
                .resourceType(Enuns.ResourceType.AGUA)
                .quantity(1)
                .build());
        TypeExchange type = TypeExchange.OFFER;

        assertThrows(RuntimeException.class, () -> rebelRules.checkAvailabiltyResource(rebel, resourceQuantities, type));
    }

    @Test
    void checkAvailabiltyResourceWhenIsNotAvailableRequested() {
        RebelRules rebelRules = new RebelRules();

        List<Resource> resources = Collections.emptyList();
        Rebel rebel = Rebel.builder().name("AnyName").resources(resources).build();
        List<ResourceQuantityDTO> resourceQuantities = List.of(ResourceQuantityDTO.builder()
                .resourceType(Enuns.ResourceType.AGUA)
                .quantity(1)
                .build());
        TypeExchange type = TypeExchange.REQUEST;

        assertThrows(RuntimeException.class, () -> rebelRules.checkAvailabiltyResource(rebel, resourceQuantities, type));
    }

    @Test
    void checkAvailabiltyResourceWhenIsAvailableOffer() {
        RebelRules rebelRules = new RebelRules();

        List<Resource> resources = List.of(Resource.builder()
                .resourceType(Enuns.ResourceType.AGUA)
                .quantity(1)
                .build());
        Rebel rebel = Rebel.builder().name("AnyName").resources(resources).build();
        List<ResourceQuantityDTO> resourceQuantities = List.of(ResourceQuantityDTO.builder()
                .resourceType(Enuns.ResourceType.AGUA)
                .quantity(1)
                .build());
        TypeExchange type = TypeExchange.OFFER;

        rebelRules.checkAvailabiltyResource(rebel, resourceQuantities, type);
    }

    @Test
    void checkAvailabiltyResourceWhenIsAvailableRequested() {
        RebelRules rebelRules = new RebelRules();

        List<Resource> resources = List.of(Resource.builder()
                .resourceType(Enuns.ResourceType.AGUA)
                .quantity(1)
                .build());
        Rebel rebel = Rebel.builder().name("AnyName").resources(resources).build();
        List<ResourceQuantityDTO> resourceQuantities = List.of(ResourceQuantityDTO.builder()
                .resourceType(Enuns.ResourceType.AGUA)
                .quantity(1)
                .build());
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