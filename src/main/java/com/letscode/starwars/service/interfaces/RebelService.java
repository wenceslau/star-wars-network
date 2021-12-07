package com.letscode.starwars.service.interfaces;

import com.letscode.starwars.model.Resource;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.dto.ExchangeResourseDTO;
import com.letscode.starwars.model.dto.ResourceCreditDTO;
import com.letscode.starwars.model.dto.ResourceDTO;
import com.letscode.starwars.model.dto.ResourceQuantityDTO;
import com.letscode.starwars.model.embedded.Localization;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RebelService {

    public Rebel insert(Rebel rebel);

    public Rebel update(Long rebelCode, Rebel rebel);

    public Rebel updateLocalization(Long rebelCode, Localization localization);

    public void markAsTraitor(Long reportByCode, Long rebelCode);

    public List<Resource> listResource(Long rebelCode);

    public Rebel findByCode(Long rebelCode);

    public List<Rebel> listAll(boolean removeTraitor);

    public boolean isTraitor(Rebel rebel);

    public Rebel executeExchangeResource(Long codeRebelOffer, Long codeRevelRequest, ExchangeResourseDTO exchangeResourseDTO);
}
