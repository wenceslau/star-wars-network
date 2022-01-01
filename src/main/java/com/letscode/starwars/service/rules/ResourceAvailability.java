package com.letscode.starwars.service.rules;

import com.letscode.starwars.model.Enuns;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.Resource;
import com.letscode.starwars.model.dto.ExchangeResourseDTO;
import com.letscode.starwars.model.dto.ResourceQuantityDTO;

import java.util.List;

public class ResourceAvailability implements  ResourceValidation{

    @Override
    public void validation(Rebel rebel, List<ResourceQuantityDTO> resourceQuantityDTOS) {

        String msg1, msg2;
        msg1 = "O rebelde [%s] não possui o recurso [%s]";
        msg2 = "O rebelde [%s] não possui a quantidade do recurso [%s]";

        //Percorre os recusos oferecidos ou requisitados para validar a disponibilidade deles no rebelde
        for (ResourceQuantityDTO reqQuant : resourceQuantityDTOS) {
            var optionalResource = rebel.getResources()
                    .stream()
                    .filter(x -> x.getResourceType().equals(reqQuant.getResourceType())).findAny();
            if (optionalResource.isEmpty())
                throw new RuntimeException(String.format(msg1, rebel.getName(), reqQuant));

            Resource res = optionalResource.get();
            if (reqQuant.getQuantity() > res.getQuantity())
                throw new RuntimeException(String.format(msg2, rebel.getName(), reqQuant));
        }

    }
}
