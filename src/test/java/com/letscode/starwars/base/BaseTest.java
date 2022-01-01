package com.letscode.starwars.base;

import com.letscode.starwars.model.Enuns;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.Resource;
import com.letscode.starwars.model.dto.ExchangeResourseDTO;
import com.letscode.starwars.model.dto.ResourceQuantityDTO;
import com.letscode.starwars.model.embedded.Localization;

import java.util.ArrayList;

public class BaseTest {

    protected  Rebel fakeRebel(Long code){
        var resources = new ArrayList<Resource>();
        resources.add(Resource.builder()
                .resourceType(Enuns.ResourceType.AGUA)
                .quantity(10)
                .build());
        Rebel rebel = Rebel.builder()
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

        rebel.setCode(code);
        return rebel;
    }

    protected ExchangeResourseDTO fakeExchangeResourceDTO() {
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