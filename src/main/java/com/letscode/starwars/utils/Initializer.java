package com.letscode.starwars.utils;

import com.letscode.starwars.model.Enuns;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.Resource;
import com.letscode.starwars.model.embedded.Localization;
import com.letscode.starwars.service.interfaces.RebelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class Initializer {

    @Autowired
    private RebelService rebelService;

    public void createRebel(){

        Rebel rebel = Rebel.builder().age(22).gender(Enuns.Gender.FEMININO).name("Jyn Erso")
                .Localization(Localization.builder().galaxy("Alderaan").latitude("99").longitude("101").build()).build();
        var listRes = new ArrayList<Resource>();
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.ARMA).quantity(3).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.MUNICAO).quantity(50).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.COMIDA).quantity(1).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.AGUA).quantity(15).build());
        rebel.setResources(listRes);
        rebelService.insert(rebel);

        rebel = Rebel.builder().age(25).gender(Enuns.Gender.MASCULINO).name("Capitão Cassian Andor")
                .Localization(Localization.builder().galaxy("Bespin").latitude("342").longitude("678").build()).build();
        listRes = new ArrayList<Resource>();
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.ARMA).quantity(4).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.MUNICAO).quantity(20).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.COMIDA).quantity(4).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.AGUA).quantity(10).build());
        rebel.setResources(listRes);
        rebelService.insert(rebel);

        rebel = Rebel.builder().age(23).gender(Enuns.Gender.MASCULINO).name("Chirrut Imw")
                .Localization(Localization.builder().galaxy("Dagobah").latitude("341").longitude("890").build()).build();
        listRes = new ArrayList<Resource>();
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.COMIDA).quantity(4).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.AGUA).quantity(10).build());
        rebel.setResources(listRes);
        rebelService.insert(rebel);

        rebel = Rebel.builder().age(40).gender(Enuns.Gender.MASCULINO).name("Baze Malbus")
                .Localization(Localization.builder().galaxy("Endor").latitude("3409").longitude("8190").build()).build();
        listRes = new ArrayList<Resource>();
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.ARMA).quantity(5).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.MUNICAO).quantity(34).build());
        rebel.setResources(listRes);
        rebelService.insert(rebel);

        rebel = Rebel.builder().age(40).gender(Enuns.Gender.MASCULINO).name("Anakim")
                .Localization(Localization.builder().galaxy("Republica").latitude("3419").longitude("81930").build()).build();
        listRes = new ArrayList<Resource>();
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.ARMA).quantity(6).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.MUNICAO).quantity(19).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.COMIDA).quantity(8).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.AGUA).quantity(12).build());
        rebel.setResources(listRes);
        rebelService.insert(rebel);

        rebel = Rebel.builder().age(40).gender(Enuns.Gender.MASCULINO).name("Obiam Kenob")
                .Localization(Localization.builder().galaxy("Republica").latitude("3419").longitude("81930").build()).build();
        listRes = new ArrayList<Resource>();
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.ARMA).quantity(9).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.MUNICAO).quantity(49).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.COMIDA).quantity(11).build());
        listRes.add(Resource.builder().resourceType(Enuns.ResourceType.AGUA).quantity(3).build());
        rebel.setResources(listRes);
        rebelService.insert(rebel);


    }
}
