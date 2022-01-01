package com.letscode.starwars.service.rules;

import com.letscode.starwars.base.BaseTest;
import com.letscode.starwars.model.Enuns;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.dto.ExchangeResourseDTO;
import com.letscode.starwars.model.dto.ResourceQuantityDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResourceAvailabilityTest extends BaseTest  {

    private ResourceAvailability resourceAvailability;

    @BeforeEach
    private void beforeEach(){
        resourceAvailability = new ResourceAvailability();
    }

    @Test
    void validation_WhenIsResourceOfferNotIsAvailable() {
        Rebel rebel = fakeRebel(1L);
        rebel.getResources().clear();
        List<ResourceQuantityDTO>  resourceQuantities = List.of(ResourceQuantityDTO.builder()
                .resourceType(Enuns.ResourceType.AGUA)
                .quantity(1)
                .build());

        String actual = "";
        String expected = "O rebelde ["+rebel.getName()+"] não possui o recurso ["+resourceQuantities.get(0)+"]";
        //assertThrows(RuntimeException.class, () -> resourceAvailability.validation(rebel, resourceQuantities));
        try {
            resourceAvailability.validation(rebel, resourceQuantities);
        } catch (RuntimeException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }

    @Test
    void validation_WhenIsQuantityResourceOfferNotIsAvailable() {
        Rebel rebel = fakeRebel(1L);
        List<ResourceQuantityDTO>  resourceQuantities = List.of(ResourceQuantityDTO.builder()
                .resourceType(Enuns.ResourceType.AGUA)
                .quantity(11)
                .build());
        String actual = "";
        String expected = "O rebelde ["+rebel.getName()+"] não possui a quantidade do recurso ["+resourceQuantities.get(0)+"]";
        //assertThrows(RuntimeException.class, () -> resourceAvailability.validation(rebel, resourceQuantities));
        try {
            resourceAvailability.validation(rebel, resourceQuantities);
        } catch (RuntimeException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);
    }
}