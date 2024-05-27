package com.uvas.uvasapi.controllers;

import com.uvas.uvasapi.controllers.dtos.EmailCreateOrUpdateDTO;
import com.uvas.uvasapi.controllers.dtos.GrupoCreateOrUpdateDTO;
import com.uvas.uvasapi.controllers.dtos.PessoaCreateOrUpdateDTO;
import com.uvas.uvasapi.controllers.dtos.PhoneCreateOrUpdateDTO;
import com.uvas.uvasapi.domain.*;
import com.uvas.uvasapi.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "pessoas")
@CrossOrigin
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private PhoneService phoneService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private GrupoService grupoService;

    @GetMapping
    public ResponseEntity<List<Pessoa>> getPessoas(){
        List<Pessoa> pessoas = pessoaService.getPessoas();

        return ResponseEntity.ok(pessoas);
    }

    @PostMapping
    public ResponseEntity<Pessoa> createPessoa(@RequestBody @Valid PessoaCreateOrUpdateDTO dto){
        Pessoa pessoa = pessoaService.createPessoa(dto.getPessoa());

        return ResponseEntity.status(201).body(pessoa);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Pessoa> getPessoaById(@PathVariable String id){
        Pessoa pessoa = pessoaService.getPessoaById(id);

        return ResponseEntity.ok(pessoa);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Pessoa> updatePessoa(@PathVariable String id, @RequestBody @Valid PessoaCreateOrUpdateDTO dto){
        Pessoa pessoa = dto.getPessoa();
        pessoa.setId(id);

        pessoaService.updatePessoa(pessoa);

        return ResponseEntity.status(200).body(pessoa);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Pessoa> deletePessoa(@PathVariable String id){
        pessoaService.deletePessoa(id);

        return ResponseEntity.noContent().build();
    }

}
