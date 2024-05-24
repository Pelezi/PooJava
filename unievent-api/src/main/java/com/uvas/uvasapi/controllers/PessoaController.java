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

        if (dto.getPhones() != null) {
            for (PhoneCreateOrUpdateDTO phoneDto : dto.getPhones()) {
                Phone phone = phoneDto.getPhone();
                if (phone.getNumero() != null && !phone.getNumero().trim().isBlank()){
                    phone.setPessoaId(pessoa);
                    phoneService.createPhone(phone);
                }
            }
        }
        if (dto.getEmails() != null) {
            for (EmailCreateOrUpdateDTO emailDto : dto.getEmails()) {
                Email email = emailDto.getEmail();
                if (email.getEmail() != null && !email.getEmail().trim().isBlank()){
                    email.setPessoaId(pessoa);
                    emailService.createEmail(email);
                }
            }
        }
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

        List<Phone> updatedPhones = new ArrayList<>();
        if (dto.getPhones() != null) {
            for (PhoneCreateOrUpdateDTO phoneDto : dto.getPhones()) {
                Phone phone = phoneDto.getPhone();
                phone.setPessoaId(pessoa);

                //Check if phone already exists and has the same pessoaId
                Phone existingPhone = phoneService.getPhoneByNumero(phone.getNumero());
                if (existingPhone != null && existingPhone.getPessoaId().getId().equals(pessoa.getId())) {
                    System.out.println("Este Telefone já está cadastrado nessa pessoa");
                } else if (existingPhone != null) {
                    System.out.println("Este Telefone já está cadastrado em outra pessoa");
                } else {
//                    phoneService.createPhone(phone);
                    updatedPhones.add(phoneService.createPhone(phone));
                }
            }
        }
        pessoa.setPhones(updatedPhones);

        List<Email> updatedEmails = new ArrayList<>();
        if (dto.getEmails() != null) {
            for (EmailCreateOrUpdateDTO emailDto : dto.getEmails()) {
                Email email = emailDto.getEmail();
                email.setPessoaId(pessoa);
                //Check if email already exists and has the same pessoaId
                Email existingEmail = emailService.getEmailByEmail(email.getEmail());
                if (existingEmail != null && existingEmail.getPessoaId().getId().equals(pessoa.getId())) {
                    System.out.println("Este Email já está cadastrado nessa pessoa");
                } else if (existingEmail != null) {
                    System.out.println("Este Email já está cadastrado em outra pessoa");
                } else {
                    //emailService.createEmail(email);
                    updatedEmails.add(emailService.createEmail(email));
                }
            }
        }
        pessoa.setEmails(updatedEmails);

        pessoaService.updatePessoa(pessoa);

        return ResponseEntity.status(200).body(pessoa);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Pessoa> deletePessoa(@PathVariable String id){
        pessoaService.deletePessoa(id);

        return ResponseEntity.noContent().build();
    }

}
