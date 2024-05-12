package com.uvas.uvasapi.repositories;

import com.uvas.uvasapi.domain.Celula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CelulaRepository extends JpaRepository<Celula, String> {

}
