package com.uvas.uvasapi.controllers;

import com.uvas.uvasapi.controllers.dtos.CelulaCreateOrUpdateDTO;
import com.uvas.uvasapi.controllers.dtos.PessoaCreateOrUpdateDTO;
import com.uvas.uvasapi.domain.Celula;
import com.uvas.uvasapi.domain.Pessoa;
import com.uvas.uvasapi.services.CelulaService;
import com.uvas.uvasapi.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "celulas")
@CrossOrigin
public class CelulaController {

    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private CelulaService celulaService;

    @GetMapping
    public ResponseEntity<List<Celula>> getCelulas(){
        List<Celula> celulas = celulaService.getCelulas();

        return ResponseEntity.ok(celulas);
    }

    @PostMapping
    public ResponseEntity<Celula> createCelula(@RequestBody @Valid CelulaCreateOrUpdateDTO dto){
        Celula celula = celulaService.createCelula(dto.getCelula());

        if (dto.getPessoas() != null) {
            for (PessoaCreateOrUpdateDTO pessoaDto : dto.getPessoas()) {
                Pessoa pessoa = pessoaDto.getPessoa();
                pessoa.setCelulaId(celula);
                pessoaService.createPessoa(pessoa);
            }
        }
        return ResponseEntity.status(201).body(celula);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Celula> getCelulaById(@PathVariable String id){
        Celula celula = celulaService.getCelulaById(id);

        return ResponseEntity.ok(celula);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Celula> updateCelula(@PathVariable String id, @RequestBody @Valid CelulaCreateOrUpdateDTO dto){
        Celula celula = dto.getCelula();
        celula.setId(id);
        celulaService.updateCelula(celula);

        return ResponseEntity.status(200).body(celula);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Celula> deleteCelula(@PathVariable String id){
        celulaService.deleteCelula(id);

        return ResponseEntity.noContent().build();
    }

}
