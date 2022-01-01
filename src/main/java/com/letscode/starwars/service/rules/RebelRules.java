package com.letscode.starwars.service.rules;

import com.letscode.starwars.model.Enuns;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.Resource;
import com.letscode.starwars.model.dto.ExchangeResourseDTO;
import com.letscode.starwars.model.dto.ResourceQuantityDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RebelRules {

    public void checkRequiredField(Rebel rebel){
        if (StringUtils.isEmpty(rebel.getName()))
            throw new RuntimeException("O nome não pode ser vazio");

        if (rebel.getAge() == null || rebel.getAge() < 0)
            throw new RuntimeException("A idade não pode ser menor zero");

    }

    /**
     * Aplica a troca dos recursos, atualizando o inventario de cada rebelde de acordo com as quantidades solicitadas
     * @param rebelOffer Rebelde que esta oferecendo recursos
     * @param rebelRequest Rebelde para quel se esta solicitando recursos
     * @param exchangeResourseDTO Objeto com os recursos oferecidos e requisitados
     */
    public void applyExchageResourses(Rebel rebelOffer, Rebel rebelRequest, ExchangeResourseDTO exchangeResourseDTO) {
        List<ResourceQuantityDTO> offers = exchangeResourseDTO.getResourcesOffer();
        List<ResourceQuantityDTO> requests = exchangeResourseDTO.getResourcesRequest();

        //Percorre a lista de recursos oferecidos e subtrai ou adiciona ao inventario do rebelde
        for (ResourceQuantityDTO rq : offers){
            var optRec1 = rebelOffer.getResources().stream().filter(x->x.getResourceType().equals(rq.getResourceType())).findAny();
            if (optRec1.isPresent()){
                Resource rec = optRec1.get();
                rec.setQuantity(rec.getQuantity() - rq.getQuantity());
            }

            var optRec2 = rebelRequest.getResources().stream().filter(x->x.getResourceType().equals(rq.getResourceType())).findAny();
            if (optRec2.isPresent()){
                Resource rec = optRec2.get();
                rec.setQuantity(rec.getQuantity() + rq.getQuantity());
            }
        }

        //Percorre a lista de recursos requisitados e subtrai ou adiciona ao inventario do rebelde
        for (ResourceQuantityDTO rq : requests){
            var optRec = rebelOffer.getResources().stream().filter(x->x.getResourceType().equals(rq.getResourceType())).findAny();
            if (optRec.isPresent()){
                Resource rec = optRec.get();
                rec.setQuantity(rec.getQuantity() + rq.getQuantity());
            }

            var optRec2 = rebelRequest.getResources().stream().filter(x->x.getResourceType().equals(rq.getResourceType())).findAny();
            if (optRec2.isPresent()){
                Resource rec = optRec2.get();
                rec.setQuantity(rec.getQuantity() - rq.getQuantity());
            }
        }
    }

    /**
     * Faz o relacionamento entre as recursos e o rebelde
     * @param rebelToSave rebeled a ser atualizado
     */
    public void relationshipRebelResource(Rebel rebelToSave) {
        if (rebelToSave.getResources() != null)
            rebelToSave.getResources().forEach(p -> p.setRebel(rebelToSave));
    }

}
