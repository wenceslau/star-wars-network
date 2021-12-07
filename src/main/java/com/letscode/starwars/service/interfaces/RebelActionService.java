package com.letscode.starwars.service.interfaces;

import com.letscode.starwars.model.RebelAction;

import java.util.List;

public interface RebelActionService {

    public List<RebelAction> list();

    public List<RebelAction> listByRebel(Long rebelCode);

    public RebelAction save(RebelAction model);

}
