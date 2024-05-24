package com.uvas.uvasapi.controllers;

import com.uvas.uvasapi.controllers.dtos.GrupoCreateOrUpdateDTO;
import com.uvas.uvasapi.domain.Grupo;
import com.uvas.uvasapi.domain.Pessoa;
import com.uvas.uvasapi.services.DiretorService;
import com.uvas.uvasapi.services.GrupoService;
import com.uvas.uvasapi.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "grupos")
@CrossOrigin
public class GrupoController {

    @Autowired
    private GrupoService grupoService;
    @Autowired
    private DiretorService diretorService;
    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<List<Grupo>> getGrupos(){
        List<Grupo> grupos = grupoService.getGrupos();

        return ResponseEntity.ok(grupos);
    }

    @PostMapping
    public ResponseEntity<Grupo> createGrupo(@RequestBody @Valid GrupoCreateOrUpdateDTO dto){
        Grupo grupo = grupoService.createGrupo(dto.getGrupo(diretorService));

        return ResponseEntity.status(201).body(grupo);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Grupo> getGrupoById(@PathVariable String id){
        Grupo grupo = grupoService.getGrupoById(id);

        return ResponseEntity.ok(grupo);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Grupo> updateGrupo(@PathVariable String id, @RequestBody @Valid GrupoCreateOrUpdateDTO dto){
        Grupo grupo = dto.getGrupo();
        grupo.setId(id);

        if (dto.getIntegrantesIds() != null) {
            for (Pessoa pessoaDto : dto.getIntegrantesIds()) {

                Pessoa existingPessoa = pessoaService.getPessoaById(pessoaDto.getId());
                if (existingPessoa != null && grupo.getIntegrantes() != null) {
                    existingPessoa.getGrupos().add(grupo);
                    pessoaService.updatePessoa(existingPessoa);
                    grupo.getIntegrantes().add(existingPessoa);
                } else if (existingPessoa != null) {
                    existingPessoa.getGrupos().add(grupo);
                    List<Pessoa> integrantes = new ArrayList<>();
                    integrantes.add(existingPessoa);
                    grupo.setIntegrantes(integrantes);
                    pessoaService.updatePessoa(existingPessoa);
                }
            }
        }

        grupoService.updateGrupo(grupo);

        return ResponseEntity.status(200).body(grupo);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Grupo> deleteGrupo(@PathVariable String id){
        grupoService.deleteGrupo(id);

        return ResponseEntity.noContent().build();
    }

}
