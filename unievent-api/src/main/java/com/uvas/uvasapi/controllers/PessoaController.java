package com.uvas.uvasapi.controllers;

import com.uvas.uvasapi.controllers.dtos.PessoaCreateOrUpdateDTO;
import com.uvas.uvasapi.domain.*;
import com.uvas.uvasapi.domain.enums.Cargo;
import com.uvas.uvasapi.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "pessoas")
@CrossOrigin
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private GrupoService grupoService;
    @Autowired
    private CelulaService celulaService;

    @GetMapping
    public ResponseEntity<List<Pessoa>> getPessoas(){
        List<Pessoa> pessoas = pessoaService.getPessoas();

        return ResponseEntity.ok(pessoas);
    }

    @PostMapping
    public ResponseEntity<Pessoa> createPessoa(@RequestBody @Valid PessoaCreateOrUpdateDTO dto){
        Pessoa pessoa = pessoaService.createPessoa(dto.getPessoa());

        return ResponseEntity.status(201).body(pessoa);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Pessoa> getPessoaById(@PathVariable String id){
        Pessoa pessoa = pessoaService.getPessoaById(id);

        return ResponseEntity.ok(pessoa);
    }

    @GetMapping(path = "cargo/{cargo}")
    public ResponseEntity<List<Pessoa>> getPessoaByCargo(@PathVariable Cargo cargo){
        List<Pessoa> pessoa = pessoaService.getPessoaByCargo(cargo);

        return ResponseEntity.ok(pessoa);
    }

    @GetMapping(path = "endereco/bairro/{bairro}")
    public ResponseEntity<List<Pessoa>> getPessoaByEnderecoIdBairro(@PathVariable String bairro){
        List<Pessoa> pessoa = pessoaService.getPessoaByEnderecoIdBairro(bairro);

        return ResponseEntity.ok(pessoa);
    }

    @GetMapping(path = "celula/{celulaId}")
    public ResponseEntity<List<Pessoa>> getPessoaByCelulaId(@PathVariable String celulaId){
        List<Pessoa> pessoa = pessoaService.getPessoaByCelulaId(celulaId);

        for (Pessoa p : pessoa) {
            p.setCelulaId(null);
            p.setGrupos(null);
        }

        return ResponseEntity.ok(pessoa);
    }

    @GetMapping(path = "grupo/{grupoId}")
    public ResponseEntity<List<Pessoa>> getPessoaByGrupoId(@PathVariable String grupoId){
        List<Pessoa> pessoa = pessoaService.getPessoaByGrupoId(grupoId);

        for (Pessoa p : pessoa) {
            p.setCelulaId(null);
            p.setGrupos(null);
        }

        return ResponseEntity.ok(pessoa);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Pessoa> updatePessoa(@PathVariable String id, @RequestBody @Valid PessoaCreateOrUpdateDTO dto){
        Pessoa pessoa = dto.getPessoa();
        pessoa.setId(id);

        pessoaService.updatePessoa(pessoa);

        return ResponseEntity.status(200).body(pessoa);
    }

    //remove pessoa from celula
    @PutMapping(path = "removeCelula/{id}")
    public ResponseEntity<Pessoa> removePessoaFromCelula(@PathVariable String id){
        Pessoa pessoa = pessoaService.getPessoaById(id);
        pessoa.setCelulaId(null);
        pessoaService.updatePessoa(pessoa);

        return ResponseEntity.status(200).body(pessoa);
    }

    //add pessoa to celula
    @PutMapping(path = "addCelula/{id}/{celulaId}")
    public ResponseEntity<Pessoa> addPessoaToCelula(@PathVariable String id, @PathVariable String celulaId){
        Pessoa pessoa = pessoaService.getPessoaById(id);
        Celula celula = celulaService.getCelulaById(celulaId);
        pessoa.setCelulaId(celula);
        pessoaService.updatePessoa(pessoa);

        return ResponseEntity.status(200).body(pessoa);
    }

    //remove pessoa from grupo
    @PutMapping(path = "removeGrupo/{id}/{grupoId}")
    public ResponseEntity<Pessoa> removePessoaFromGrupo(@PathVariable String id, @PathVariable String grupoId){
        Pessoa pessoa = pessoaService.getPessoaById(id);
        Grupo grupo = grupoService.getGrupoById(grupoId);
        grupo.getIntegrantes().removeIf(p -> p.getId().equals(id));
        grupoService.updateGrupo(grupo);

        return ResponseEntity.status(200).body(pessoa);
    }

    //add pessoa to grupo
    @PutMapping(path = "addGrupo/{id}/{grupoId}")
    public ResponseEntity<Pessoa> addPessoaToGrupo(@PathVariable String id, @PathVariable String grupoId){
        Pessoa pessoa = pessoaService.getPessoaById(id);
        Grupo grupo = grupoService.getGrupoById(grupoId);
        grupo.getIntegrantes().add(pessoa);
        grupoService.updateGrupo(grupo);

        return ResponseEntity.status(200).body(pessoa);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Pessoa> deletePessoa(@PathVariable String id){
        pessoaService.deletePessoa(id);

        return ResponseEntity.noContent().build();
    }

}
