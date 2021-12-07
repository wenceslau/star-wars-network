package com.letscode.starwars.repository;

import com.letscode.starwars.model.RebelAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RebelActionRepository extends JpaRepository<RebelAction, Long> {

    List<RebelAction> findAllByIdRecordAndNameObjectOrderByCodeDesc(Long code, String nameObject);

}
