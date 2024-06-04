package com.uvas.uvasapi.services;

import com.uvas.uvasapi.domain.Celula;
import com.uvas.uvasapi.domain.enums.DiaDaSemana;
import com.uvas.uvasapi.exceptions.NotFoundException;
import com.uvas.uvasapi.repositories.CelulaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class CelulaService {

    @Autowired
    private CelulaRepository celulaRepository;

    public List<Celula> getCelulas(){
        return celulaRepository.findAll();
    }

    @Transactional(rollbackOn = Exception.class)
    public Celula createCelula(Celula celula) {

        celulaRepository.save(celula);
        return celula;
    }

    public Celula getCelulaById(String id) {
        Optional<Celula> celulaExists = celulaRepository.findById(id);

        return celulaExists.orElseThrow(() -> new NotFoundException("Celula não encontrada"));
    }

    public List<Celula> getCelulaByLiderId(String liderId) {
        return celulaRepository.findByLiderIdId(liderId);
    }

    public List<Celula> getCelulaByDiscipuladorId(String discipuladorId) {
        return celulaRepository.findByDiscipuladorIdId(discipuladorId);
    }

    public List<Celula> getCelulaByDiaDaSemana(DiaDaSemana diaDaSemana) {
        return celulaRepository.findByDiaDaSemana(diaDaSemana);
    }

    public List<Celula> getCelulaByHorario(LocalTime horario) {
        return celulaRepository.findByHorario(horario);
    }

    public List<Celula> getCelulaByEnderecoIdBairro(String bairro) {
        return celulaRepository.findByEnderecoIdBairro(bairro);
    }

    @Transactional(rollbackOn = Exception.class)
    public Celula updateCelula(Celula celula) {

        celulaRepository.save(celula);
        return celula;
    }

    public void deleteCelula(String id) {
        Optional<Celula> celulaExists = celulaRepository.findById(id);

        Celula celula = celulaExists.orElseThrow(() -> new NotFoundException("Celula não encontrada"));
        celulaRepository.delete(celula);
    }

}
