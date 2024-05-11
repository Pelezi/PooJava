package com.uvas.uvasapi.controllers;

import com.uvas.uvasapi.controllers.dtos.EnderecoCreateOrUpdateDTO;
import com.uvas.uvasapi.domain.Endereco;
import com.uvas.uvasapi.services.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "enderecos")
@CrossOrigin
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping
    public ResponseEntity<List<Endereco>> getEnderecos(){
        List<Endereco> enderecos = enderecoService.getEnderecos();

        return ResponseEntity.ok(enderecos);
    }

    @PostMapping
    public ResponseEntity<Endereco> createEndereco(@RequestBody @Valid EnderecoCreateOrUpdateDTO dto){
        Endereco endereco = enderecoService.createEndereco(dto.getEndereco());

        return ResponseEntity.status(201).body(endereco);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Endereco> getEnderecoById(@PathVariable String id){
        Endereco endereco = enderecoService.getEnderecoById(id);

        return ResponseEntity.ok(endereco);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Endereco> updateEndereco(@PathVariable String id, @RequestBody @Valid EnderecoCreateOrUpdateDTO dto){
        Endereco endereco = dto.getEndereco();
        endereco.setId(id);
        enderecoService.updateEndereco(endereco);

        return ResponseEntity.status(200).body(endereco);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Endereco> deleteEndereco(@PathVariable String id){
        enderecoService.deleteEndereco(id);

        return ResponseEntity.noContent().build();
    }

}
