package com.letscode.starwars.service;

import com.letscode.starwars.model.RebelAction;
import com.letscode.starwars.repository.RebelActionRepository;
import com.letscode.starwars.service.interfaces.RebelActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Classe para registro das ações de um rebelde
 * @author Wenceslau
 */
@Service("rebelActionService")
public class RebelActionServiceImpl implements RebelActionService {

    @Autowired
    private RebelActionRepository userActionRepository;

    /**
     * retorna todas as ações executadas
     * @return lista de ações
     */
    @Override
    public List<RebelAction> list() {
        return userActionRepository.findAll();
    }

    /**
     * Retorna todas as ações executadas por um rebelde em order de ação
     * @param rebelCode codigo do rebelde
     * @return lista de ações
     */
    @Override
    public List<RebelAction> listByRebel(Long rebelCode) {
        return userActionRepository.findAllByIdRecordAndNameObjectOrderByCodeDesc(rebelCode, "Rebel");
    }


    /**
     * Salva uma ação de um rebelde
     * @param model RebelAction
     * @return RebelAction
     */
    @Override
    public RebelAction save(RebelAction model) {
        return userActionRepository.save(model);
    }
}
