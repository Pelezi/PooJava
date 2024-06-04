package com.uvas.uvasapi.repositories;

import com.uvas.uvasapi.domain.Pessoa;
import com.uvas.uvasapi.domain.enums.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, String> {
    //find a lis of pessoas by celulaId
    List<Pessoa> findByCelulaIdId(String celulaId);
    //find a list of pessoas by grupoId
    List<Pessoa> findByGruposId(String grupoId);
    //find a list of pessoas by cargo
    List<Pessoa> findByCargo(Cargo cargo);
    //find a list of pessoas by enderecoId.bairro
    List<Pessoa> findByEnderecoIdBairro(String bairro);
}
