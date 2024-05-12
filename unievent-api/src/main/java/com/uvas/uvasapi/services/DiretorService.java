package com.uvas.uvasapi.services;

import com.uvas.uvasapi.domain.Diretor;
import com.uvas.uvasapi.exceptions.NotFoundException;
import com.uvas.uvasapi.repositories.DiretorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiretorService {

    @Autowired
    private DiretorRepository diretorRepository;

    public List<Diretor> getDiretores(){
        return diretorRepository.findAll();
    }

    @Transactional(rollbackOn = Exception.class)
    public Diretor createDiretor(Diretor diretor) {

        diretorRepository.save(diretor);
        return diretor;
    }

    public Diretor getDiretorById(String id) {
        Optional<Diretor> diretorExists = diretorRepository.findById(id);

        return diretorExists.orElseThrow(() -> new NotFoundException("Diretor não encontrado"));
    }

    @Transactional(rollbackOn = Exception.class)
    public Diretor updateDiretor(Diretor diretor) {

        diretorRepository.save(diretor);
        return diretor;
    }

    public void deleteDiretor(String id) {
        Optional<Diretor> diretorExists = diretorRepository.findById(id);

        Diretor diretor = diretorExists.orElseThrow(() -> new NotFoundException("Diretor não encontrado"));
        diretorRepository.delete(diretor);
    }

}
