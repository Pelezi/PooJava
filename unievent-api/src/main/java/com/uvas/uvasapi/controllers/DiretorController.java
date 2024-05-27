package com.uvas.uvasapi.controllers;

import com.uvas.uvasapi.controllers.dtos.DiretorCreateOrUpdateDTO;
import com.uvas.uvasapi.domain.Diretor;
import com.uvas.uvasapi.domain.Grupo;
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
@RequestMapping(value = "diretores")
@CrossOrigin
public class DiretorController {

    @Autowired
    private DiretorService diretorService;
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private GrupoService grupoService;

    private void handleGrupo(@RequestBody @Valid DiretorCreateOrUpdateDTO dto, Diretor diretor) {
        if (dto.getGrupos() != null) {
            for (Grupo grupoDto : dto.getGrupos()) {
                Grupo existingGrupo = grupoService.getGrupoById(grupoDto.getId());
                if (existingGrupo != null && diretor.getGrupos() != null) {
                    existingGrupo.setDiretorId(diretor);
                    grupoService.updateGrupo(existingGrupo);
                    diretor.getGrupos().add(existingGrupo);
                } else if (existingGrupo != null) {
                    existingGrupo.setDiretorId(diretor);
                    List<Grupo> grupos = new ArrayList<>();
                    grupos.add(existingGrupo);
                    diretor.setGrupos(grupos);
                    grupoService.updateGrupo(existingGrupo);

                }
            }
        }
    }

    @GetMapping
    public ResponseEntity<List<Diretor>> getDiretores(){
        List<Diretor> diretores = diretorService.getDiretores();

        return ResponseEntity.ok(diretores);
    }

    @PostMapping
    public ResponseEntity<Diretor> createDiretor(@RequestBody @Valid DiretorCreateOrUpdateDTO dto){
        Diretor diretor = diretorService.createDiretor(dto.getDiretor(pessoaService));

        handleGrupo(dto, diretor);

        return ResponseEntity.status(201).body(diretor);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Diretor> getDiretorById(@PathVariable String id){
        Diretor diretor = diretorService.getDiretorById(id);

        return ResponseEntity.ok(diretor);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Diretor> updateDiretor(@PathVariable String id, @RequestBody @Valid DiretorCreateOrUpdateDTO dto){
        Diretor diretor = dto.getDiretor(pessoaService);
        diretor.setId(id);

        handleGrupo(dto, diretor);

        diretorService.updateDiretor(diretor);

        return ResponseEntity.status(200).body(diretor);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Diretor> deleteDiretor(@PathVariable String id){
        diretorService.deleteDiretor(id);

        return ResponseEntity.noContent().build();
    }

}
