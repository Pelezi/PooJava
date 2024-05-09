package com.uvas.uvasapi.services;

import com.uvas.uvasapi.domain.Pessoa;
import com.uvas.uvasapi.exceptions.NotFoundException;
import com.uvas.uvasapi.repositories.PessoaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> getPessoas(){
        return pessoaRepository.findAll();
    }

    @Transactional(rollbackOn = Exception.class)
    public Pessoa createPessoa(Pessoa pessoa) {

        pessoaRepository.save(pessoa);
        return pessoa;
    }

    public Pessoa getPessoaById(String id) {
        Optional<Pessoa> pessoaExists = pessoaRepository.findById(id);

        return pessoaExists.orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));
    }

    @Transactional(rollbackOn = Exception.class)
    public Pessoa updatePessoa(Pessoa pessoa) {

        pessoaRepository.save(pessoa);
        return pessoa;
    }

    public void deletePessoa(String id) {
        Optional<Pessoa> pessoaExists = pessoaRepository.findById(id);

        Pessoa pessoa = pessoaExists.orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));
        pessoaRepository.delete(pessoa);
    }

}
