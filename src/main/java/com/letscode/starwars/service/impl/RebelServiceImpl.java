package com.letscode.starwars.service.impl;

import com.letscode.starwars.base.Base;
import com.letscode.starwars.model.Enuns;
import com.letscode.starwars.model.Resource;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.dto.ResourceCreditDTO;
import com.letscode.starwars.model.dto.ResourceDTO;
import com.letscode.starwars.model.dto.ResourceQuantityDTO;
import com.letscode.starwars.model.embedded.Localization;
import com.letscode.starwars.repository.RebelRepository;
import com.letscode.starwars.service.RebelService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RebelServiceImpl extends Base implements RebelService {

    @Autowired
    private RebelRepository rebelRepository;

    @Override
    public List<Rebel> listAll() {
        List<Rebel> lst =  rebelRepository.findAll();
        lst.removeIf(x->x.getSuspect() >= 3);
        return rebelRepository.findAll();
    }

    @Override
    public List<ResourceCreditDTO> listResourceCredit() {
        var listResources = new ArrayList<ResourceCreditDTO>();
        for(Enuns.ResourceType resourceType : Enuns.ResourceType.values())
            listResources.add(ResourceCreditDTO.builder()
                    .resourceType(resourceType)
                    .credit(resourceType.getCredit())
                    .build());
        return listResources;
    }

    @Override
    public List<Resource> listInventory(Long code) {
        Rebel rebel = findByCode(code);
        return rebel.getResources();
    }

    @Override
    public Rebel findByCode(Long code) {
        if (code == null)
            throw new InvalidParameterException("");

        var optional = rebelRepository.findById(code);
        if (!optional.isPresent())
            throw new EmptyResultDataAccessException(""+code, 1);

        return optional.get();
    }

    @Override
    public Rebel save(Rebel rebel) {
        info("INSERT: " + rebel);
        relationshipRebelResource(rebel, rebel);
        return rebelRepository.save(rebel);
    }

    @Override
    public Rebel edit(Long code, Rebel rebel) {
        info("UPDATE: " + rebel);
        Rebel rebelDataBase = findByCode(rebel.getCode());
        BeanUtils.copyProperties(rebel, rebelDataBase, new String[] { "code", "resources" });
        return rebelRepository.save(rebel);
    }

    @Override
    public Rebel updateLocalization(Long code, Localization localization) {
        Rebel rebel = findByCode(code);
        rebel.setLocalization(localization);
        rebelRepository.save(rebel);
        return rebel;
    }

    @Override
    public void isTraitor(Long code) {
        Rebel rebel = findByCode(code);
        rebel.setSuspect(rebel.getSuspect()+1);
        rebelRepository.save(rebel);
    }

    @Override
    public List<ResourceQuantityDTO> executeExchangeResource(Long codeRebelOffer, Long codeRebelRequest, List<ResourceQuantityDTO> offers, List<ResourceQuantityDTO> requests) {
        checkAvailabiltyResource(codeRebelOffer, offers, "OFFERS");
        checkAvailabiltyResource(codeRebelOffer, offers, "REQUEST");

        Integer creditsAvailableOffer = 0;
        for (ResourceQuantityDTO resourceQ : offers) {
            creditsAvailableOffer += resourceQ.getResourceType().getCredit() * resourceQ.getQuantity();

        }
        Integer creditsAvailableRequest = 0;
        for (ResourceQuantityDTO resourceQ : requests){
            creditsAvailableRequest += resourceQ.getResourceType().getCredit() * resourceQ.getQuantity();

    }
        if (creditsAvailableOffer != creditsAvailableRequest)
            throw new RuntimeException("A quantiadade de creditos que voce esta oferencendo deve ser a mesma quantidade de creditos solicitada");

        Rebel rebelOffer = findByCode(codeRebelOffer);
        Rebel rebelRquest = findByCode(codeRebelRequest);

        String msg = "";
        for (Resource reso : rebelRquest.getResources()) {

        }

        return null;
    }

    private void checkAvailabiltyResource(Long codeRebel, List<ResourceQuantityDTO> resources, String type) {
        Rebel rebel = findByCode(codeRebel);

        String msg1, msg2;
        if (type.equals("OFFER")) {
            msg1 = "Você [%s] não possui o recurso [%s] que esta oferecendo";
            msg2 = "Você [%s] não possui o quantidade do recurso [%s] que esta oferecendo";
        }
        else {
            msg1 = "O rebelde [%s] não possui o recurso  [%s] que você solicitou";
            msg2 = "O rebelde [%s] não possui a quantidade do recurso  [%s] que você solicitou";
        }

        for (ResourceQuantityDTO resource : resources) {
            var optionalResource = rebel.getResources()
                    .stream()
                    .filter(x -> x.getResourceType().equals(resource.getResourceType())).findAny();
            if (optionalResource.isEmpty())
                throw new RuntimeException(String.format(msg1, rebel.getName(), resource));

            Resource res = optionalResource.get();
            if (resource.getQuantity() > res.getQuantity())
                throw new RuntimeException(String.format(msg2, rebel.getName(), resource));
        }


    }

    /**
     * Faz o relacionamento entre as recursos e o rebelde
     * @param rebel rebelde
     * @param rebelToSave rebeled a ser atualizado
     */
    private void relationshipRebelResource(Rebel rebel, Rebel rebelToSave) {
        if (rebelToSave.getResources() != null)
            rebelToSave.getResources().forEach(p -> p.setRebel(rebelToSave));
    }
}
