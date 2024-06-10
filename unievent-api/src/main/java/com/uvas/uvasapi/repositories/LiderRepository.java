package com.uvas.uvasapi.repositories;

import com.uvas.uvasapi.domain.Lider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LiderRepository extends JpaRepository<Lider, String> {
    public Optional<Lider> findByPessoaIdId(String pessoaId);
    public Optional<Lider> findByCelulasId(String celulaId);

}
