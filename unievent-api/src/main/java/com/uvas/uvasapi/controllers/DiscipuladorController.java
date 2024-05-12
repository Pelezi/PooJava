package com.uvas.uvasapi.controllers;

import com.uvas.uvasapi.controllers.dtos.DiscipuladorCreateOrUpdateDTO;
import com.uvas.uvasapi.domain.Discipulador;
import com.uvas.uvasapi.services.DiscipuladorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "discipuladores")
@CrossOrigin
public class DiscipuladorController {

    @Autowired
    private DiscipuladorService discipuladorService;

    @GetMapping
    public ResponseEntity<List<Discipulador>> getDiscipuladores(){
        List<Discipulador> discipuladores = discipuladorService.getDiscipuladores();

        return ResponseEntity.ok(discipuladores);
    }

    @PostMapping
    public ResponseEntity<Discipulador> createDiscipulador(@RequestBody @Valid DiscipuladorCreateOrUpdateDTO dto){
        Discipulador discipulador = discipuladorService.createDiscipulador(dto.getDiscipulador());

        return ResponseEntity.status(201).body(discipulador);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Discipulador> getDiscipuladorById(@PathVariable String id){
        Discipulador discipulador = discipuladorService.getDiscipuladorById(id);

        return ResponseEntity.ok(discipulador);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Discipulador> updateDiscipulador(@PathVariable String id, @RequestBody @Valid DiscipuladorCreateOrUpdateDTO dto){
        Discipulador discipulador = dto.getDiscipulador();
        discipulador.setId(id);
        discipuladorService.updateDiscipulador(discipulador);

        return ResponseEntity.status(200).body(discipulador);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Discipulador> deleteDiscipulador(@PathVariable String id){
        discipuladorService.deleteDiscipulador(id);

        return ResponseEntity.noContent().build();
    }

}
