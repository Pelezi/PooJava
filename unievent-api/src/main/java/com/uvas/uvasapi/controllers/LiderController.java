package com.uvas.uvasapi.controllers;

import com.uvas.uvasapi.controllers.dtos.LiderCreateOrUpdateDTO;
import com.uvas.uvasapi.domain.Celula;
import com.uvas.uvasapi.domain.Lider;
import com.uvas.uvasapi.services.CelulaService;
import com.uvas.uvasapi.services.LiderService;
import com.uvas.uvasapi.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "lideres")
@CrossOrigin
public class LiderController {

    @Autowired
    private LiderService liderService;
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private CelulaService celulaService;

    private void handleCelula(@RequestBody @Valid LiderCreateOrUpdateDTO dto, Lider lider) {
        if (dto.getCelulas() != null) {
            for (Celula celulaDto : dto.getCelulas()) {
                Celula existingCelula = celulaService.getCelulaById(celulaDto.getId());
                if (existingCelula != null && lider.getCelulas() != null) {
                    existingCelula.setLiderId(lider);
                    celulaService.updateCelula(existingCelula);
                    lider.getCelulas().add(existingCelula);
                } else if (existingCelula != null) {
                    existingCelula.setLiderId(lider);
                    List<Celula> celulas = new ArrayList<>();
                    celulas.add(existingCelula);
                    lider.setCelulas(celulas);
                    celulaService.updateCelula(existingCelula);

                }
            }
        }
    }

    @GetMapping
    public ResponseEntity<List<Lider>> getLideres(){
        List<Lider> lideres = liderService.getLideres();

        return ResponseEntity.ok(lideres);
    }

    @PostMapping
    public ResponseEntity<Lider> createLider(@RequestBody @Valid LiderCreateOrUpdateDTO dto){
        Lider lider = liderService.createLider(dto.getLider(pessoaService));

        handleCelula(dto, lider);

        return ResponseEntity.status(201).body(lider);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Lider> getLiderById(@PathVariable String id){
        Lider lider = liderService.getLiderById(id);

        return ResponseEntity.ok(lider);
    }

    @GetMapping(path = "pessoa/{pessoaId}")
    public ResponseEntity<Lider> getLiderByPessoaId(@PathVariable String pessoaId){
        Lider lider = liderService.getLiderByPessoaId(pessoaId);

        return ResponseEntity.ok(lider);
    }

    @GetMapping(path = "celula/{celulaId}")
    public ResponseEntity<Lider> getLiderByCelulaId(@PathVariable String celulaId){
        Lider lider = liderService.getLiderByCelulaId(celulaId);

        return ResponseEntity.ok(lider);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Lider> updateLider(@PathVariable String id, @RequestBody @Valid LiderCreateOrUpdateDTO dto){
        Lider lider = dto.getLider(pessoaService);
        lider.setId(id);

        handleCelula(dto, lider);
        //Remove liderId from celulas that are not in the new list
        List<Celula> celulas = celulaService.getCelulas();
        for (Celula celula : celulas) {
            if (celula.getLiderId() != null && celula.getLiderId().getId().equals(id) && lider.getCelulas() != null && !lider.getCelulas().contains(celula)) {
                celula.setLiderId(null);
                celulaService.updateCelula(celula);
            }
        }

        liderService.updateLider(lider);

        return ResponseEntity.status(200).body(lider);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Lider> deleteLider(@PathVariable String id){
        //Remove liderId from celulas that have this lider
        List<Celula> celulas = celulaService.getCelulas();
        for (Celula celula : celulas) {
            if (celula.getLiderId() != null && celula.getLiderId().getId().equals(id)) {
                celula.setLiderId(null);
                celulaService.updateCelula(celula);
            }
        }
        //set pessoaId to null
        Lider lider = liderService.getLiderById(id);
        lider.setPessoaId(null);
        liderService.updateLider(lider);
        liderService.deleteLider(id);

        return ResponseEntity.noContent().build();
    }

}
