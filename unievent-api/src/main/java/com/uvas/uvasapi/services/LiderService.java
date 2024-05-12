package com.uvas.uvasapi.services;

import com.uvas.uvasapi.domain.Lider;
import com.uvas.uvasapi.exceptions.NotFoundException;
import com.uvas.uvasapi.repositories.LiderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LiderService {

    @Autowired
    private LiderRepository liderRepository;

    public List<Lider> getLideres(){
        return liderRepository.findAll();
    }

    @Transactional(rollbackOn = Exception.class)
    public Lider createLider(Lider lider) {

        liderRepository.save(lider);
        return lider;
    }

    public Lider getLiderById(String id) {
        Optional<Lider> liderExists = liderRepository.findById(id);

        return liderExists.orElseThrow(() -> new NotFoundException("Lider não encontrado"));
    }

    @Transactional(rollbackOn = Exception.class)
    public Lider updateLider(Lider lider) {

        liderRepository.save(lider);
        return lider;
    }

    public void deleteLider(String id) {
        Optional<Lider> liderExists = liderRepository.findById(id);

        Lider lider = liderExists.orElseThrow(() -> new NotFoundException("Lider não encontrado"));
        liderRepository.delete(lider);
    }

}
