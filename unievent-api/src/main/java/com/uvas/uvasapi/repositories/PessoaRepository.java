package com.uvas.uvasapi.repositories;

import com.uvas.uvasapi.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, String> {

    public Optional<Pessoa> findPessoaById(String id);

}
