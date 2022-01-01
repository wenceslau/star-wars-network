package com.letscode.starwars.service.rules;

import com.letscode.starwars.base.BaseTest;
import com.letscode.starwars.model.Enuns;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.dto.ResourceQuantityDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResourceQuantityTest extends BaseTest {

    private ResourceQuantity resourceQuantity;

    @BeforeEach
    private void beforeEach(){
        resourceQuantity = new ResourceQuantity();
    }

    @Test
    void validation_quantityResourceAvailableNeedToBeGreaterZero() {
        Rebel rebel = fakeRebel(1L);
        List<ResourceQuantityDTO>  resourceQuantities = List.of(ResourceQuantityDTO.builder()
                .resourceType(Enuns.ResourceType.AGUA)
                .quantity(0)
                .build());

        String actual = "";
        String expected = "A quantidade de recursos é obrigatoria";
        //assertThrows(RuntimeException.class, () -> resourceAvailability.validation(rebel, resourceQuantities));
        try {
            resourceQuantity.validation(rebel, resourceQuantities);
        } catch (RuntimeException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);


    }

    @Test
    void validation_quantityCreditResourceNeedToBeEnough() {
        Rebel rebel = fakeRebel(1L);
        rebel.getResources().get(0).setQuantity(1);
        List<ResourceQuantityDTO>  resourceQuantities = List.of(ResourceQuantityDTO.builder()
                .resourceType(Enuns.ResourceType.AGUA)
                .quantity(2)
                .build());

        String actual = "";
        String expected = "O total de creditos não esta disponivel";
        //assertThrows(RuntimeException.class, () -> resourceAvailability.validation(rebel, resourceQuantities));
        try {
            resourceQuantity.validation(rebel, resourceQuantities);
        } catch (RuntimeException e) {
            actual = e.getMessage();
        }
        assertEquals(expected, actual);


    }
}