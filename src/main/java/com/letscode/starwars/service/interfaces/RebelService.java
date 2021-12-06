package com.letscode.starwars.service.interfaces;

import com.letscode.starwars.model.Resource;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.dto.ResourceCreditDTO;
import com.letscode.starwars.model.dto.ResourceDTO;
import com.letscode.starwars.model.dto.ResourceQuantityDTO;
import com.letscode.starwars.model.embedded.Localization;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RebelService {

    public Rebel save(Rebel rebel);

    public Rebel edit(Long code, Rebel rebel);

    public Rebel updateLocalization(Long code, Localization localization);

    public void markAsTraitor(Long reportByCode, Long code);

    public List<Resource> listInventory(Long code);

    public Rebel findByCode(Long code);

    public List<Rebel> listAll(boolean removeTraitor);

    public boolean isTraitor(Rebel rebel);

    public List<ResourceQuantityDTO> executeExchangeResource(Long codeRebelOffer, Long codeRevelRequest, List<ResourceQuantityDTO> offers, List<ResourceQuantityDTO> requests);
}
