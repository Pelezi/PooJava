package com.uvas.uvasapi.services;

import com.uvas.uvasapi.domain.Discipulador;
import com.uvas.uvasapi.exceptions.NotFoundException;
import com.uvas.uvasapi.repositories.DiscipuladorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscipuladorService {

    @Autowired
    private DiscipuladorRepository discipuladorRepository;

    public List<Discipulador> getDiscipuladores(){
        return discipuladorRepository.findAll();
    }

    @Transactional(rollbackOn = Exception.class)
    public Discipulador createDiscipulador(Discipulador discipulador) {

        discipuladorRepository.save(discipulador);
        return discipulador;
    }

    public Discipulador getDiscipuladorById(String id) {
        Optional<Discipulador> discipuladorExists = discipuladorRepository.findById(id);

        return discipuladorExists.orElseThrow(() -> new NotFoundException("Discipulador não encontrado"));
    }

    public Discipulador getDiscipuladorByPessoaId(String pessoaId) {
        Optional<Discipulador> discipuladorExists = discipuladorRepository.findByPessoaIdId(pessoaId);

        return discipuladorExists.orElseThrow(() -> new NotFoundException("Discipulador não encontrado"));
    }

    @Transactional(rollbackOn = Exception.class)
    public Discipulador updateDiscipulador(Discipulador discipulador) {

        discipuladorRepository.save(discipulador);
        return discipulador;
    }

    public void deleteDiscipulador(String id) {
        Optional<Discipulador> discipuladorExists = discipuladorRepository.findById(id);

        Discipulador discipulador = discipuladorExists.orElseThrow(() -> new NotFoundException("Discipulador não encontrado"));
        discipuladorRepository.delete(discipulador);
    }

}
