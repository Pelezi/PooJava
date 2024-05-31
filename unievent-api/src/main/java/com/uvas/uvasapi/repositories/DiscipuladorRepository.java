package com.uvas.uvasapi.repositories;

import com.uvas.uvasapi.domain.Discipulador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscipuladorRepository extends JpaRepository<Discipulador, String> {
    public Optional<Discipulador> findByPessoaIdId(String pessoaId);

}
