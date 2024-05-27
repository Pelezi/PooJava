package com.uvas.uvasapi.controllers;

import com.uvas.uvasapi.controllers.dtos.CelulaCreateOrUpdateDTO;
import com.uvas.uvasapi.controllers.dtos.PessoaCreateOrUpdateDTO;
import com.uvas.uvasapi.domain.Celula;
import com.uvas.uvasapi.domain.Pessoa;
import com.uvas.uvasapi.services.CelulaService;
import com.uvas.uvasapi.services.DiscipuladorService;
import com.uvas.uvasapi.services.LiderService;
import com.uvas.uvasapi.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "celulas")
@CrossOrigin
public class CelulaController {

    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private CelulaService celulaService;
    @Autowired
    private DiscipuladorService discipuladorService;
    @Autowired
    private LiderService liderService;

    @GetMapping
    public ResponseEntity<List<Celula>> getCelulas(){
        List<Celula> celulas = celulaService.getCelulas();

        return ResponseEntity.ok(celulas);
    }

    @PostMapping
    public ResponseEntity<Celula> createCelula(@RequestBody @Valid CelulaCreateOrUpdateDTO dto){
        Celula celula = celulaService.createCelula(dto.getCelula(liderService, discipuladorService));

        return ResponseEntity.status(201).body(celula);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Celula> getCelulaById(@PathVariable String id){
        Celula celula = celulaService.getCelulaById(id);

        return ResponseEntity.ok(celula);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Celula> updateCelula(@PathVariable String id, @RequestBody @Valid CelulaCreateOrUpdateDTO dto){
        Celula celula = dto.getCelula(liderService, discipuladorService);
        celula.setId(id);

        if (dto.getPessoas() != null) {
            for (Pessoa pessoaDto : dto.getPessoas()) {

                Pessoa existingPessoa = pessoaService.getPessoaById(pessoaDto.getId());
                if(existingPessoa != null && celula.getPessoas() != null) {
                    existingPessoa.setCelulaId(celula);
                    pessoaService.updatePessoa(existingPessoa);
                    celula.getPessoas().add(existingPessoa);
                } else if (existingPessoa != null){
                    existingPessoa.setCelulaId(celula);
                    List<Pessoa> pessoas = new ArrayList<>();
                    pessoas.add(existingPessoa);
                    celula.setPessoas(pessoas);
                    pessoaService.updatePessoa(existingPessoa);
                }
            }
        }
        if (dto.getLiderId() != null) {

        }
        celulaService.updateCelula(celula);

        return ResponseEntity.status(200).body(celula);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Celula> deleteCelula(@PathVariable String id){

        //Remove celulaId from pessoas that are in the celula
        List<Pessoa> pessoas = pessoaService.getPessoas();
        for (Pessoa pessoa : pessoas) {
            if (pessoa.getCelulaId() != null && pessoa.getCelulaId().getId().equals(id)) {
                pessoa.setCelulaId(null);
                pessoaService.updatePessoa(pessoa);
            }
        }

        celulaService.deleteCelula(id);

        return ResponseEntity.noContent().build();
    }

}
