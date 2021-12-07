package com.letscode.starwars.service;

import com.letscode.starwars.base.Base;
import com.letscode.starwars.model.Enuns;
import com.letscode.starwars.model.Resource;
import com.letscode.starwars.model.Rebel;
import com.letscode.starwars.model.dto.ExchangeResourseDTO;
import com.letscode.starwars.model.dto.ResourceQuantityDTO;
import com.letscode.starwars.model.embedded.Localization;
import com.letscode.starwars.repository.RebelRepository;
import com.letscode.starwars.service.interfaces.RebelService;
import com.letscode.starwars.service.rules.RebelRules;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.letscode.starwars.model.Enuns.TypeExchange;

import java.util.List;

/**
 * Classe com as regras de negocio de manipulação do rebelde e seus recursos
 * @author Wenceslau
 */
@Service("rebelService")
public class RebelServiceImpl extends Base implements RebelService {

    @Autowired
    private RebelRepository rebelRepository;

    @Autowired
    private RebelRules rebelRules;

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
        return rebel.getSuspectBy1() != null && rebel.getSuspectBy2() != null && rebel.getSuspectBy3() != null;
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
        rebelRules.checkRequiredField(rebel);
        rebelRules.relationshipRebelResource(rebel, rebel);
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
        rebelRules.checkRequiredField(rebel);
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

        rebelRules.checkAvailabiltyResource(rebelOffer, offers, TypeExchange.OFFER);
        rebelRules.checkAvailabiltyResource(rebelRequest, requests, TypeExchange.REQUEST);
        rebelRules.checkQuantityResource(rebelOffer, rebelRequest, exchangeResourseDTO);


        rebelRules.applyExchageResourses(rebelOffer, rebelRequest, exchangeResourseDTO);

        //Salva o rebelde apos aplicar a negociação de recursos
        save(rebelOffer);
        save(rebelRequest);

        return findByCode(codeRebelOffer);
    }

    /*
     * Metodos expostos pelo repositorio
     * Usados para que outros services ou classes nao acessem
     * o repositorio do rebel diretamente
     */

    /**
     * Chama o repositorio para salvar a entidade na base
     * @param rebel rebelde
     * @return rebelde
     */
    public Rebel save(Rebel rebel){
        return rebelRepository.save(rebel);
    }
}
