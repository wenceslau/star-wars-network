package com.letscode.starwars.utils;

import com.letscode.starwars.model.Enuns;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.Resource;
import com.letscode.starwars.model.embedded.Localization;
import com.letscode.starwars.repository.RebelRepository;
import com.letscode.starwars.service.RebelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class Initializer {

    @Autowired
    private RebelService rebelService;

    public void createRebel(){

        Rebel rebel = Rebel.builder().age(22).gender(Enuns.Gender.FEMALE).name("Jyn Erso").suspect(0)
                .Localization(Localization.builder().galaxy("Alderaan").latitude("99").longitude("101").build()).build();
        var listRes = new ArrayList<Resource>();
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.GUN).quantity(3).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.MUNITION).quantity(50).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.FOOD).quantity(1).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.WATER).quantity(15).build());
        rebel.setResources(listRes);
        rebelService.save(rebel);

        rebel = Rebel.builder().age(25).gender(Enuns.Gender.MALE).name("Capitão Cassian Andor").suspect(0)
                .Localization(Localization.builder().galaxy("Bespin").latitude("342").longitude("678").build()).build();
        listRes = new ArrayList<Resource>();
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.GUN).quantity(4).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.MUNITION).quantity(20).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.FOOD).quantity(4).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.WATER).quantity(10).build());
        rebel.setResources(listRes);
        rebelService.save(rebel);

        rebel = Rebel.builder().age(23).gender(Enuns.Gender.MALE).name("Chirrut Imw").suspect(0)
                .Localization(Localization.builder().galaxy("Dagobah").latitude("341").longitude("890").build()).build();
        listRes = new ArrayList<Resource>();
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.FOOD).quantity(4).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.WATER).quantity(10).build());
        rebel.setResources(listRes);
        rebelService.save(rebel);

        rebel = Rebel.builder().age(40).gender(Enuns.Gender.MALE).name("Baze Malbus").suspect(0)
                .Localization(Localization.builder().galaxy("Endor").latitude("3409").longitude("8190").build()).build();
        listRes = new ArrayList<Resource>();
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.GUN).quantity(5).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.MUNITION).quantity(34).build());
        rebel.setResources(listRes);
        rebelService.save(rebel);


    }
}
