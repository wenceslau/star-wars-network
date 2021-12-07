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
     * Verifica a disponilidades dos recursos disponiveis
     * @param rebel rebelde
     * @param resources lista de recursos oferecidos ou requisitados
     * @param type tipo da lista de recursos, OFFER or REQUEST
     */
    public void checkAvailabiltyResource(Rebel rebel, List<ResourceQuantityDTO> resources, Enuns.TypeExchange type) {

        String msg1, msg2;
        if (type.equals(Enuns.TypeExchange.OFFER)) {
            msg1 = "Você [%s] não possui o recurso [%s] que esta oferecendo";
            msg2 = "Você [%s] não possui o quantidade do recurso [%s] que esta oferecendo";
        }
        else {
            msg1 = "O rebelde [%s] não possui o recurso  [%s] que você solicitou";
            msg2 = "O rebelde [%s] não possui a quantidade do recurso  [%s] que você solicitou";
        }

        //Percorre os recusos oferecidos ou requisitados para validar a disponibilidade deles no rebelde
        for (ResourceQuantityDTO reqQuant : resources) {
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

    /**
     * Verifica se o rebelde possui a quantidade de recursos oferecidos ou requisitados para poder negociar
     * Verifica se o rebelde possui o total de creditos dos recursos oferecidos ou requisitados
     * @param rebelOffer Rebelde que esta oferecendo recursos
     * @param rebelRequest Rebelde para quel se esta solicitando recursos
     * @param exchangeResourseDTO Objeto com os recursos oferecidos e requisitados
     */
    public void checkQuantityResource(Rebel rebelOffer, Rebel rebelRequest, ExchangeResourseDTO exchangeResourseDTO) {
        List<ResourceQuantityDTO> offers = exchangeResourseDTO.getResourcesOffer();
        List<ResourceQuantityDTO> requests = exchangeResourseDTO.getResourcesRequest();

        //Valida se a quantidade não é igual a zero
        for(Resource resource : rebelOffer.getResources())
            if (resource.getQuantity() == 0)
                throw new RuntimeException("Você não pode oferecer recursos sem quantidade definida");

        //Valida se a quantidade não é igual a zero
        for(Resource resource : rebelRequest.getResources())
            if (resource.getQuantity() == 0)
                throw new RuntimeException("Você não pode solicitar recursos sem quantidade definida");

        //Identifica o total de creditos dos recursos do rebelde tem a oferecer
        Integer totalOfferAvailable = 0;
        for(Resource resource : rebelOffer.getResources())
            totalOfferAvailable += resource.getQuantity() * resource.getResourceType().getCredit();

        //Identifica o total de creditos oferecidos
        Integer creditsOffer = 0;
        for (ResourceQuantityDTO resourceQ : offers)
            creditsOffer += resourceQ.getResourceType().getCredit() * resourceQ.getQuantity();

        //Verifica se o rebelde tem recursos suficientes para suprir o oferecido
        if (creditsOffer >  totalOfferAvailable)
            throw new RuntimeException("Você não possui o total de creditos que esta oferecendo");

        //Identifica o total de creditos dos recursos do rebelde a ser requisitado
        Integer totalRequestAvailable = 0;
        for(Resource resource : rebelRequest.getResources())
            totalRequestAvailable += resource.getQuantity() * resource.getResourceType().getCredit();

        //Identifica o total de creditos requisitados
        Integer creditsRequest = 0;
        for (ResourceQuantityDTO resourceQ : requests)
            creditsRequest += resourceQ.getResourceType().getCredit() * resourceQ.getQuantity();

        //Verifica se o rebelde que esta sendo requisitados tem recursos suficientes para suprir o pedido
        if (creditsRequest >  totalRequestAvailable)
            throw new RuntimeException("O rebelde ["+rebelRequest.getName()+"] não possui credito suficiente para negociar");
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
     * @param rebel rebelde
     * @param rebelToSave rebeled a ser atualizado
     */
    public void relationshipRebelResource(Rebel rebel, Rebel rebelToSave) {
        if (rebelToSave.getResources() != null)
            rebelToSave.getResources().forEach(p -> p.setRebel(rebelToSave));
    }

}
