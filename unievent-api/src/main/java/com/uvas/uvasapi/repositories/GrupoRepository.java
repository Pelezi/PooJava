package com.uvas.uvasapi.repositories;

import com.uvas.uvasapi.domain.Grupo;
import com.uvas.uvasapi.domain.enums.GrupoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, String> {
    //find a list of grupos by tipo
    List<Grupo> findByGrupoType(GrupoType grupoType);
    //find a list of grupos by diretorId.id
    List<Grupo> findByDiretorIdId(String diretorId);
    //find a list of grupos by pessoaId.id
    List<Grupo> findByintegrantesId(String pessoaId);
}
