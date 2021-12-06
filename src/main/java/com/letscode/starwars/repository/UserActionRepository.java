package com.letscode.starwars.repository;

import com.letscode.starwars.model.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActionRepository extends JpaRepository<UserAction, Long> {



}
