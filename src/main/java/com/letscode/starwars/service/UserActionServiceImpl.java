package com.letscode.starwars.service;

import com.letscode.starwars.model.UserAction;
import com.letscode.starwars.repository.UserActionRepository;
import com.letscode.starwars.service.interfaces.UserActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userActionService")
public class UserActionServiceImpl implements UserActionService {

    @Autowired
    private UserActionRepository userActionRepository;

    @Override
    public List<UserAction> list() {
        return userActionRepository.findAll();
    }

    @Override
    public UserAction save(UserAction model) {
        return userActionRepository.save(model);
    }
}
