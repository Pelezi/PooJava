package com.uvas.uvasapi.services;

import com.uvas.uvasapi.domain.Endereco;
import com.uvas.uvasapi.exceptions.NotFoundException;
import com.uvas.uvasapi.repositories.EnderecoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<Endereco> getEnderecos(){
        return enderecoRepository.findAll();
    }

    @Transactional(rollbackOn = Exception.class)
    public Endereco createEndereco(Endereco endereco) {

        enderecoRepository.save(endereco);
        return endereco;
    }

    public Endereco getEnderecoById(String id) {
        Optional<Endereco> enderecoExists = enderecoRepository.findById(id);

        return enderecoExists.orElseThrow(() -> new NotFoundException("Endereco não encontrado"));
    }

    @Transactional(rollbackOn = Exception.class)
    public Endereco updateEndereco(Endereco endereco) {

        enderecoRepository.save(endereco);
        return endereco;
    }

    public void deleteEndereco(String id) {
        Optional<Endereco> enderecoExists = enderecoRepository.findById(id);

        Endereco endereco = enderecoExists.orElseThrow(() -> new NotFoundException("Endereco não encontrado"));
        enderecoRepository.delete(endereco);
    }

}
