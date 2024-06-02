package com.uvas.uvasapi.repositories;

import com.uvas.uvasapi.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, String> {
    //find a lis of pessoas by celulaId
    List<Pessoa> findByCelulaIdId(String celulaId);
}
