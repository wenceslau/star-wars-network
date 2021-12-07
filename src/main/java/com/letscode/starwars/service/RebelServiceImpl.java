package com.letscode.starwars.service;

import com.letscode.starwars.base.Base;
import com.letscode.starwars.model.Enuns;
import com.letscode.starwars.model.Resource;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.dto.ExchangeResourseDTO;
import com.letscode.starwars.model.dto.ResourceCreditDTO;
import com.letscode.starwars.model.dto.ResourceQuantityDTO;
import com.letscode.starwars.model.embedded.Localization;
import com.letscode.starwars.repository.RebelRepository;
import com.letscode.starwars.service.interfaces.RebelService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe com as regras de negocio de manipulação do rebelde e seus recursos
 * @author Wenceslau
 */
@Service("rebelService")
public class RebelServiceImpl extends Base implements RebelService {

    @Autowired
    private RebelRepository rebelRepository;

    /**
     * Lista todos os rebeldes, removendo ou não os traidores
     * @param removeTraitor true or false
     * @return lista rebeldes
     */
    @Override
    public List<Rebel> listAll(boolean removeTraitor) {
        List<Rebel> lst =  rebelRepository.findAll();
        if (removeTraitor)
            lst.removeIf(x-> isTraitor(x));
        return lst;
    }

    /**
     * Identifica se o rebelde é um traidor
     * @param rebel rebelde
     * @return true or false
     */
    @Override
    public boolean isTraitor(Rebel rebel) {
        return rebel.getSuspectBy1() != null && rebel.getSuspectBy2() != null && rebel.getSuspectBy2() != null;
    }

    /**
     * Lista os recursos de um rebelde, seu inventario
     * @param rebelCode codigo do rebelde
     * @return list resources
     */
    @Override
    public List<Resource> listResource(Long rebelCode) {
        Rebel rebel = findByCode(rebelCode);
        return rebel.getResources();
    }

    /**
     * Recupera um rebelde pelo codigo
     * @param rebelCode codigo do rebelde
     * @return rebel
     */
    @Override
    public Rebel findByCode(Long rebelCode) {
        if (rebelCode == null)
            throw new RuntimeException("O codigo é obrigatório");

        var optional = rebelRepository.findById(rebelCode);
        if (!optional.isPresent())
            throw new EmptyResultDataAccessException("Rebelde não econtrado com codigo " + rebelCode, 1);

        return optional.get();
    }

    /**
     * Insere um rebelde na resistencia
     * @param rebel rebelde
     * @return rebelde
     */
    @Override
    public Rebel insert(Rebel rebel) {
        info("INSERT: " + rebel);
        relationshipRebelResource(rebel, rebel);
        return save(rebel);
    }

    /**
     * Atualiza um rebelde na resistencia
     * @param rebelCode codigo do rebelde
     * @param rebel rebelde
     * @return rebelde
     */
    @Override
    public Rebel update(Long rebelCode, Rebel rebel) {
        info("UPDATE: " + rebel);
        Rebel rebelDataBase = findByCode(rebel.getCode());
        BeanUtils.copyProperties(rebel, rebelDataBase, new String[] { "code", "resources" });
        return save(rebel);
    }

    /**
     * Atualiza a localização de um rebelde
     * @param rebelCode codigo do rebelde
     * @param localization locaização
     * @return rebel
     */
    @Override
    public Rebel updateLocalization(Long rebelCode, Localization localization) {
        Rebel rebel = findByCode(rebelCode);
        rebel.setLocalization(localization);
        rebelRepository.save(rebel);
        return rebel;
    }

    /**
     * Indica que um rebelde é traidor
     * @param reportByCode codigo do rebelde que esta reportando o traidor
     * @param suspectCode codigo do rebelde suspeito de traição
     */
    @Override
    public void markAsTraitor(Long reportByCode, Long suspectCode) {
        Rebel rebel = findByCode(suspectCode);

        if (reportByCode.equals(rebel.getSuspectBy1())||
                reportByCode.equals(rebel.getSuspectBy2()) ||
                reportByCode.equals(rebel.getSuspectBy3())){
            throw new RuntimeException("Você ja denunciou esse rebelde como traidor, não é necessário uma nova denuncia");
        }

        if (rebel.getSuspectBy1() == null)
            rebel.setSuspectBy1(reportByCode);
        else if (rebel.getSuspectBy2() == null)
            rebel.setSuspectBy2(reportByCode);
        else if (rebel.getSuspectBy3() == null)
            rebel.setSuspectBy3(reportByCode);

        rebelRepository.save(rebel);
    }

    /**
     * Negocia recursos entre rebeldes
     * @param codeRebelOffer codigo do rebelde que esta oferencendo recursos
     * @param codeRebelRequest codigo do rebelde para quem esta solicitando troca de recursos
     * @param exchangeResourseDTO Objeto com os recursos oferecidos e requisitados
     * @return rebelde
     */
    @Override
    public Rebel executeExchangeResource(Long codeRebelOffer, Long codeRebelRequest, ExchangeResourseDTO exchangeResourseDTO) {

        Rebel rebelOffer = findByCode(codeRebelOffer);
        if (isTraitor(rebelOffer))
            throw new RuntimeException("Você foi considerado traidor, e não pode negociar recursos");

        Rebel rebelRequest = findByCode(codeRebelRequest);
        if (isTraitor(rebelRequest))
            throw new RuntimeException("O rebelde "+rebelRequest.getName()+" foi considerado traidor, e não pode negociar recursos");


        List<ResourceQuantityDTO> offers = exchangeResourseDTO.getResourcesOffer();
        List<ResourceQuantityDTO> requests = exchangeResourseDTO.getResourcesRequest();

        checkAvailabiltyResource(rebelOffer, offers, "OFFER");
        checkAvailabiltyResource(rebelRequest, requests, "REQUEST");
        checkQuantityResource(rebelOffer, rebelRequest, exchangeResourseDTO);
        applyExchageResourses(rebelOffer, rebelRequest, exchangeResourseDTO);

        return findByCode(codeRebelOffer);
    }

    /*
     * Metodos privados
     */

    /**
     * Verifica a disponilidades dos recursos disponiveis
     * @param rebel rebelde
     * @param resources lista de recursos oferecidos ou requisitados
     * @param type tipo da lista de recursos, OFFER or REQUEST
     */
    private void checkAvailabiltyResource(Rebel rebel, List<ResourceQuantityDTO> resources, String type) {

        String msg1, msg2;
        if (type.equals("OFFER")) {
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
    private void checkQuantityResource(Rebel rebelOffer, Rebel rebelRequest, ExchangeResourseDTO exchangeResourseDTO) {
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

//        Integer creditsAvailableOffer = 0;
//        for (ResourceQuantityDTO resourceQ : offers)
//            creditsAvailableOffer += resourceQ.getResourceType().getCredit() * resourceQ.getQuantity();
//
//        Integer creditsAvailableRequest = 0;
//        for (ResourceQuantityDTO resourceQ : requests)
//            creditsAvailableRequest += resourceQ.getResourceType().getCredit() * resourceQ.getQuantity();
//
//        if (creditsAvailableOffer > creditsAvailableRequest)
//            throw new RuntimeException("A quantiadade de creditos que voce esta oferencendo deve ser a mesma quantidade de creditos solicitada");

    }

    /**
     * Aplica a troca dos recursos, atualizando o inventario de cada rebelde de acordo com as quantidades solicitadas
     * @param rebelOffer Rebelde que esta oferecendo recursos
     * @param rebelRequest Rebelde para quel se esta solicitando recursos
     * @param exchangeResourseDTO Objeto com os recursos oferecidos e requisitados
     */
    private void applyExchageResourses(Rebel rebelOffer, Rebel rebelRequest, ExchangeResourseDTO exchangeResourseDTO) {
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

        //Salva o rebelde
        save(rebelOffer);
        save(rebelRequest);
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

    /*
     * Metodos expostos pelo repositorio
     */

    /**
     * Chama o repositorio para salvar a entidade na base
     * @param rebel rebelde
     * @return rebelde
     */
    private Rebel save(Rebel rebel){
        return rebelRepository.save(rebel);
    }
}
