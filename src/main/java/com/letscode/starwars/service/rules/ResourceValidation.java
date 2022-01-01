package com.letscode.starwars.service.rules;

import com.letscode.starwars.model.Enuns;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.dto.ExchangeResourseDTO;
import com.letscode.starwars.model.dto.ResourceQuantityDTO;

import java.util.List;

public interface ResourceValidation {

    void validation(Rebel rebel, List<ResourceQuantityDTO> resourceQuantityDTOS);
}
