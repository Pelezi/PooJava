package com.uvas.uvasapi.services;

import com.uvas.uvasapi.domain.Grupo;
import com.uvas.uvasapi.exceptions.NotFoundException;
import com.uvas.uvasapi.repositories.GrupoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    public List<Grupo> getGrupos(){
        return grupoRepository.findAll();
    }

    @Transactional(rollbackOn = Exception.class)
    public Grupo createGrupo(Grupo grupo) {

        grupoRepository.save(grupo);
        return grupo;
    }

    public Grupo getGrupoById(String id) {
        Optional<Grupo> grupoExists = grupoRepository.findById(id);

        return grupoExists.orElseThrow(() -> new NotFoundException("Grupo não encontrado"));
    }

    @Transactional(rollbackOn = Exception.class)
    public Grupo updateGrupo(Grupo grupo) {

        grupoRepository.save(grupo);
        return grupo;
    }

    public void deleteGrupo(String id) {
        Optional<Grupo> grupoExists = grupoRepository.findById(id);

        Grupo grupo = grupoExists.orElseThrow(() -> new NotFoundException("Grupo não encontrado"));
        grupoRepository.delete(grupo);
    }

}
