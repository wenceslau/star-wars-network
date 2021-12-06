package com.letscode.starwars.repository;

import com.letscode.starwars.model.Rebel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RebelRepository extends JpaRepository<Rebel, Long>  {

}
