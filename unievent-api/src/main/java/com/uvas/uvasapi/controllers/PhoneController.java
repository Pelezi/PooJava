package com.uvas.uvasapi.controllers;

import com.uvas.uvasapi.controllers.dtos.PhoneCreateOrUpdateDTO;
import com.uvas.uvasapi.domain.Phone;
import com.uvas.uvasapi.services.PessoaService;
import com.uvas.uvasapi.services.PhoneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "phones")
@CrossOrigin
public class PhoneController {

    @Autowired
    private PhoneService phoneService;
    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<List<Phone>> getPhones(){
        List<Phone> phones = phoneService.getPhones();

        return ResponseEntity.ok(phones);
    }

    @PostMapping
    public ResponseEntity<Phone> createPhone(@RequestBody @Valid PhoneCreateOrUpdateDTO dto){
        Phone phone = phoneService.createPhone(dto.getPhone(pessoaService));

        return ResponseEntity.status(201).body(phone);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Phone> getPhoneById(@PathVariable String id){
        Phone phone = phoneService.getPhoneById(id);

        return ResponseEntity.ok(phone);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Phone> updatePhone(@PathVariable String id, @RequestBody @Valid PhoneCreateOrUpdateDTO dto){
        Phone phone = dto.getPhone(pessoaService);
        phone.setId(id);
        phoneService.updatePhone(phone);

        return ResponseEntity.status(200).body(phone);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Phone> deletePhone(@PathVariable String id){
        Phone phone = phoneService.getPhoneById(id);
        phone.setPessoaId(null);
        phoneService.deletePhone(id);

        return ResponseEntity.noContent().build();
    }

}
