package com.uvas.uvasapi.repositories;

import com.uvas.uvasapi.domain.Discipulador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscipuladorRepository extends JpaRepository<Discipulador, String> {

}
