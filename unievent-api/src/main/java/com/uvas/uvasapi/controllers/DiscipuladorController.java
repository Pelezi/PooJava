package com.uvas.uvasapi.controllers;

import com.uvas.uvasapi.controllers.dtos.DiscipuladorCreateOrUpdateDTO;
import com.uvas.uvasapi.domain.Celula;
import com.uvas.uvasapi.domain.Discipulador;
import com.uvas.uvasapi.services.CelulaService;
import com.uvas.uvasapi.services.DiscipuladorService;
import com.uvas.uvasapi.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "discipuladores")
@CrossOrigin
public class DiscipuladorController {

    @Autowired
    private DiscipuladorService discipuladorService;
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private CelulaService celulaService;

    private void handleCelula(@RequestBody @Valid DiscipuladorCreateOrUpdateDTO dto, Discipulador discipulador) {
        if (dto.getCelulas() != null) {
            for (Celula celulaDto : dto.getCelulas()) {
                Celula existingCelula = celulaService.getCelulaById(celulaDto.getId());
                if (existingCelula != null && discipulador.getCelulas() != null) {
                    existingCelula.setDiscipuladorId(discipulador);
                    celulaService.updateCelula(existingCelula);
                    discipulador.getCelulas().add(existingCelula);
                } else if (existingCelula != null) {
                    existingCelula.setDiscipuladorId(discipulador);
                    List<Celula> celulas = new ArrayList<>();
                    celulas.add(existingCelula);
                    discipulador.setCelulas(celulas);
                    celulaService.updateCelula(existingCelula);

                }
            }
        }
    }

    @GetMapping
    public ResponseEntity<List<Discipulador>> getDiscipuladores(){
        List<Discipulador> discipuladores = discipuladorService.getDiscipuladores();

        return ResponseEntity.ok(discipuladores);
    }

    @PostMapping
    public ResponseEntity<Discipulador> createDiscipulador(@RequestBody @Valid DiscipuladorCreateOrUpdateDTO dto){
        Discipulador discipulador = discipuladorService.createDiscipulador(dto.getDiscipulador(pessoaService));

        handleCelula(dto, discipulador);

        return ResponseEntity.status(201).body(discipulador);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Discipulador> getDiscipuladorById(@PathVariable String id){
        Discipulador discipulador = discipuladorService.getDiscipuladorById(id);

        return ResponseEntity.ok(discipulador);
    }

    @GetMapping(path = "pessoa/{pessoaId}")
    public ResponseEntity<Discipulador> getDiscipuladorByPessoaId(@PathVariable String pessoaId){
        Discipulador discipulador = discipuladorService.getDiscipuladorByPessoaId(pessoaId);

        return ResponseEntity.ok(discipulador);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Discipulador> updateDiscipulador(@PathVariable String id, @RequestBody @Valid DiscipuladorCreateOrUpdateDTO dto){
        Discipulador discipulador = dto.getDiscipulador(pessoaService);
        discipulador.setId(id);

        handleCelula(dto, discipulador);
        //Remove discipuladorId from celulas that are not in the new list
        List<Celula> celulas = celulaService.getCelulas();
        for (Celula celula : celulas) {
            if (celula.getDiscipuladorId() != null && celula.getDiscipuladorId().getId().equals(id) && !discipulador.getCelulas().contains(celula)) {
                celula.setDiscipuladorId(null);
                celulaService.updateCelula(celula);
            }
        }

        discipuladorService.updateDiscipulador(discipulador);

        return ResponseEntity.status(200).body(discipulador);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Discipulador> deleteDiscipulador(@PathVariable String id){
        //Remove discipuladorId from celulas that have this discipulador
        List<Celula> celulas = celulaService.getCelulas();
        for (Celula celula : celulas) {
            if (celula.getDiscipuladorId() != null && celula.getDiscipuladorId().getId().equals(id)) {
                celula.setDiscipuladorId(null);
                celulaService.updateCelula(celula);
            }
        }
        //set pessoaId to null
        Discipulador discipulador = discipuladorService.getDiscipuladorById(id);
        discipulador.setPessoaId(null);
        discipuladorService.updateDiscipulador(discipulador);
        discipuladorService.deleteDiscipulador(id);

        return ResponseEntity.noContent().build();
    }

}
