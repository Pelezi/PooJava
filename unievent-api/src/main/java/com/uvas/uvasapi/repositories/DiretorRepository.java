package com.uvas.uvasapi.repositories;

import com.uvas.uvasapi.domain.Diretor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiretorRepository extends JpaRepository<Diretor, String> {
    //get diretor by pessoaId
    Diretor findByPessoaIdId(String pessoaId);
}
