package com.uvas.uvasapi.repositories;

import com.uvas.uvasapi.domain.Lider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiderRepository extends JpaRepository<Lider, String> {

}
