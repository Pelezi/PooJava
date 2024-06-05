package com.uvas.uvasapi.controllers;

import com.uvas.uvasapi.controllers.dtos.GrupoCreateOrUpdateDTO;
import com.uvas.uvasapi.domain.Grupo;
import com.uvas.uvasapi.domain.Pessoa;
import com.uvas.uvasapi.domain.enums.GrupoType;
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

    private void handleIntegrantes(@RequestBody @Valid GrupoCreateOrUpdateDTO dto, Grupo grupo) {
        if (dto.getIntegrantes() != null) {
            for (Pessoa pessoaDto : dto.getIntegrantes()) {
                Pessoa existingPessoa = pessoaService.getPessoaById(pessoaDto.getId());
                if (existingPessoa != null) {
                    if (grupo.getIntegrantes() == null) {
                        grupo.setIntegrantes(new ArrayList<>());
                    }
                    grupo.getIntegrantes().add(existingPessoa);
                }
            }
            grupoService.updateGrupo(grupo);
        }
    }

    @GetMapping
    public ResponseEntity<List<Grupo>> getGrupos(){
        List<Grupo> grupos = grupoService.getGrupos();

        return ResponseEntity.ok(grupos);
    }

    @PostMapping
    public ResponseEntity<Grupo> createGrupo(@RequestBody @Valid GrupoCreateOrUpdateDTO dto){
        Grupo grupo = grupoService.createGrupo(dto.getGrupo(diretorService));

        if (dto.getIntegrantes() != null){
            handleIntegrantes(dto, grupo);
        }

        return ResponseEntity.status(201).body(grupo);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Grupo> getGrupoById(@PathVariable String id){
        Grupo grupo = grupoService.getGrupoById(id);

        return ResponseEntity.ok(grupo);
    }

    @GetMapping(path = "diretor/{diretorId}")
    public ResponseEntity<List<Grupo>> getGrupoByDiretorId(@PathVariable String diretorId){
        List<Grupo> grupos = grupoService.getGrupoByDiretorId(diretorId);

        return ResponseEntity.ok(grupos);
    }

    @GetMapping(path = "grupoType/{grupoType}")
    public ResponseEntity<List<Grupo>> getGrupoByGrupoType(@PathVariable GrupoType grupoType){
        List<Grupo> grupos = grupoService.getGrupoByGrupoType(grupoType);

        return ResponseEntity.ok(grupos);
    }

    @GetMapping(path = "integrante/{pessoaId}")
    public ResponseEntity<List<Grupo>> getGrupoByIntegrantesId(@PathVariable String pessoaId){
        List<Grupo> grupos = grupoService.getGrupoByIntegrantesId(pessoaId);

        return ResponseEntity.ok(grupos);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Grupo> updateGrupo(@PathVariable String id, @RequestBody @Valid GrupoCreateOrUpdateDTO dto){
        Grupo grupo = dto.getGrupo(diretorService);
        grupo.setId(id);

        if (dto.getIntegrantes() != null) {
            handleIntegrantes(dto, grupo);
            //Remove grupoId from pessoas that are not in the grupo
            List<Pessoa> pessoas = pessoaService.getPessoas();
            for (Pessoa pessoa : pessoas) {
                if (pessoa.getGrupos() != null) {
                    pessoa.getGrupos().removeIf(pessoaGrupo -> pessoaGrupo.getId().equals(id) && !grupo.getIntegrantes().contains(pessoaGrupo));
                    pessoaService.updatePessoa(pessoa);
                }
            }
        }

        grupoService.updateGrupo(grupo);

        return ResponseEntity.status(200).body(grupo);
    }


    @DeleteMapping(path = "{id}")
    public ResponseEntity<Grupo> deleteGrupo(@PathVariable String id){
        //Remove grupoId from pessoas that are in the grupo
        List<Pessoa> pessoas = pessoaService.getPessoas();
        for (Pessoa pessoa : pessoas) {
            if (pessoa.getGrupos() != null) {
                pessoa.getGrupos().removeIf(grupo -> grupo.getId().equals(id));
                pessoaService.updatePessoa(pessoa);
            }
        }
        grupoService.deleteGrupo(id);

        return ResponseEntity.noContent().build();
    }

    //remove diretor from grupo
    @PutMapping(path = "removeDiretor/{id}")
    public ResponseEntity<Grupo> removeDiretorFromGrupo(@PathVariable String id){
        Grupo grupo = grupoService.getGrupoById(id);
        grupo.setDiretorId(null);
        grupoService.updateGrupo(grupo);

        return ResponseEntity.status(200).body(grupo);
    }

}
