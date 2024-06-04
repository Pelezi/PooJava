package com.uvas.uvasapi.controllers;

import com.uvas.uvasapi.controllers.dtos.CelulaCreateOrUpdateDTO;
import com.uvas.uvasapi.controllers.dtos.PessoaCreateOrUpdateDTO;
import com.uvas.uvasapi.domain.Celula;
import com.uvas.uvasapi.domain.Discipulador;
import com.uvas.uvasapi.domain.Lider;
import com.uvas.uvasapi.domain.Pessoa;
import com.uvas.uvasapi.domain.enums.DiaDaSemana;
import com.uvas.uvasapi.services.CelulaService;
import com.uvas.uvasapi.services.DiscipuladorService;
import com.uvas.uvasapi.services.LiderService;
import com.uvas.uvasapi.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
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

    private void handlePessoas(@RequestBody @Valid CelulaCreateOrUpdateDTO dto, Celula celula) {
        if (dto.getPessoas() != null) {
            for (Pessoa pessoaDto : dto.getPessoas()) {
                Pessoa existingPessoa = pessoaService.getPessoaById(pessoaDto.getId());
                if (existingPessoa != null) {
                    existingPessoa.setCelulaId(celula);
                    pessoaService.updatePessoa(existingPessoa);
                }
            }
        }
    }

    @GetMapping
    public ResponseEntity<List<Celula>> getCelulas(){
        List<Celula> celulas = celulaService.getCelulas();

        return ResponseEntity.ok(celulas);
    }

    @PostMapping
    public ResponseEntity<Celula> createCelula(@RequestBody @Valid CelulaCreateOrUpdateDTO dto){
        Celula celula = celulaService.createCelula(dto.getCelula(liderService, discipuladorService));

        handlePessoas(dto, celula);

        return ResponseEntity.status(201).body(celula);
    }


    @GetMapping(path = "{id}")
    public ResponseEntity<Celula> getCelulaById(@PathVariable String id){
        Celula celula = celulaService.getCelulaById(id);

        return ResponseEntity.ok(celula);
    }

    @GetMapping(path = "lider/{liderId}")
    public ResponseEntity<List<Celula>> getCelulaByLiderId(@PathVariable String liderId){
        List<Celula> celula = celulaService.getCelulaByLiderId(liderId);

        return ResponseEntity.ok(celula);
    }

    @GetMapping(path = "discipulador/{discipuladorId}")
    public ResponseEntity<List<Celula>> getCelulaByDiscipuladorId(@PathVariable String discipuladorId){
        List<Celula> celula = celulaService.getCelulaByDiscipuladorId(discipuladorId);

        return ResponseEntity.ok(celula);
    }

    @GetMapping(path = "diaDaSemana/{diaDaSemana}")
    public ResponseEntity<List<Celula>> getCelulaByDiaDaSemana(@PathVariable DiaDaSemana diaDaSemana){
        List<Celula> celula = celulaService.getCelulaByDiaDaSemana(diaDaSemana);

        return ResponseEntity.ok(celula);
    }

    @GetMapping(path = "horario/{horario}")
    public ResponseEntity<List<Celula>> getCelulaByHorario(@PathVariable LocalTime horario){
        List<Celula> celula = celulaService.getCelulaByHorario(horario);

        return ResponseEntity.ok(celula);
    }

    @GetMapping(path = "enderecoIdBairro/{bairro}")
    public ResponseEntity<List<Celula>> getCelulaByEnderecoIdBairro(@PathVariable String bairro){
        List<Celula> celula = celulaService.getCelulaByEnderecoIdBairro(bairro);

        return ResponseEntity.ok(celula);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Celula> updateCelula(@PathVariable String id, @RequestBody CelulaCreateOrUpdateDTO dto){
        Celula celula = dto.getCelula(liderService, discipuladorService);
        celula.setId(id);

        handlePessoas(dto, celula);
        //Remove celulaId from pessoas that are not in the celula
        List<Pessoa> pessoas = pessoaService.getPessoas();
        for (Pessoa pessoa : pessoas) {
            if (pessoa.getCelulaId() != null && pessoa.getCelulaId().getId().equals(id) &&celula.getPessoas() != null && !celula.getPessoas().contains(pessoa)) {
                pessoa.setCelulaId(null);
                pessoaService.updatePessoa(pessoa);
            }
        }
        if (dto.getLiderId() != null) {
            Lider lider = liderService.getLiderById(dto.getLiderId().getId());
            List<Celula> celulas = lider.getCelulas();
            if (celulas != null) {
                celulas.removeIf(celula1 -> celula1.getId().equals(id));
                celulas.add(celula);
                lider.setCelulas(celulas);
                liderService.updateLider(lider);
            }
        }
        if (dto.getDiscipuladorId() != null) {
            Discipulador discipulador = discipuladorService.getDiscipuladorById(dto.getDiscipuladorId().getId());
            List<Celula> celulas = discipulador.getCelulas();
            if (celulas != null) {
                celulas.removeIf(celula1 -> celula1.getId().equals(id));
                celulas.add(celula);
                discipulador.setCelulas(celulas);
                discipuladorService.updateDiscipulador(discipulador);
            }
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

    //remove lider from celula
    @PutMapping(path = "removeLider/{id}")
    public ResponseEntity<Celula> removeLiderFromCelula(@PathVariable String id){
        Celula celula = celulaService.getCelulaById(id);
        celula.setLiderId(null);
        celulaService.updateCelula(celula);

        return ResponseEntity.status(200).body(celula);
    }

    //remove discipulador from celula
    @PutMapping(path = "removeDiscipulador/{id}")
    public ResponseEntity<Celula> removeDiscipuladorFromCelula(@PathVariable String id){
        Celula celula = celulaService.getCelulaById(id);
        celula.setDiscipuladorId(null);
        celulaService.updateCelula(celula);

        return ResponseEntity.status(200).body(celula);
    }

}
