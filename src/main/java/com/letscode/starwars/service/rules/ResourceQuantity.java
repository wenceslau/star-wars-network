package com.letscode.starwars.service.rules;

import com.letscode.starwars.model.Enuns;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.Resource;
import com.letscode.starwars.model.dto.ExchangeResourseDTO;
import com.letscode.starwars.model.dto.ResourceQuantityDTO;

import java.util.List;

public class ResourceQuantity implements  ResourceValidation{

    @Override
    public void validation(Rebel rebel, List<ResourceQuantityDTO> resourceQuantityDTOS) {

        //Valida se a quantidade não é igual a zero oferecida ou requisitada
        for(ResourceQuantityDTO resourceQ : resourceQuantityDTOS)
            if (resourceQ.getQuantity() == 0)
                throw new RuntimeException("A quantidade de recursos é obrigatoria");

        //Identifica o total de creditos dos recursos do rebelde tem a oferecer
        Integer totalAvailable = 0;
        for(Resource resource : rebel.getResources())
            totalAvailable += resource.getQuantity() * resource.getResourceType().getCredit();

        //Identifica o total de creditos oferecidos ou requisitados
        Integer credits = 0;
        for (ResourceQuantityDTO resourceQ : resourceQuantityDTOS)
            credits += resourceQ.getResourceType().getCredit() * resourceQ.getQuantity();

        //Verifica se o rebelde tem recursos suficientes para suprir o oferecido
        if (credits >  totalAvailable)
            throw new RuntimeException("O total de creditos não esta disponivel");

    }
}
