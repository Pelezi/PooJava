package com.uvas.uvasapi.controllers;

import com.uvas.uvasapi.controllers.dtos.DiretorCreateOrUpdateDTO;
import com.uvas.uvasapi.domain.Diretor;
import com.uvas.uvasapi.services.DiretorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "diretores")
@CrossOrigin
public class DiretorController {

    @Autowired
    private DiretorService diretorService;

    @GetMapping
    public ResponseEntity<List<Diretor>> getDiretores(){
        List<Diretor> diretores = diretorService.getDiretores();

        return ResponseEntity.ok(diretores);
    }

    @PostMapping
    public ResponseEntity<Diretor> createDiretor(@RequestBody @Valid DiretorCreateOrUpdateDTO dto){
        Diretor diretor = diretorService.createDiretor(dto.getDiretor());

        return ResponseEntity.status(201).body(diretor);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Diretor> getDiretorById(@PathVariable String id){
        Diretor diretor = diretorService.getDiretorById(id);

        return ResponseEntity.ok(diretor);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Diretor> updateDiretor(@PathVariable String id, @RequestBody @Valid DiretorCreateOrUpdateDTO dto){
        Diretor diretor = dto.getDiretor();
        diretor.setId(id);
        diretorService.updateDiretor(diretor);

        return ResponseEntity.status(200).body(diretor);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Diretor> deleteDiretor(@PathVariable String id){
        diretorService.deleteDiretor(id);

        return ResponseEntity.noContent().build();
    }

}
