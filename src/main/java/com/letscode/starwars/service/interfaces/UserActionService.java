package com.letscode.starwars.service.interfaces;

import com.letscode.starwars.model.UserAction;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserActionService {

    public List<UserAction> list();

    public UserAction save(UserAction model);

}
