package com.uvas.uvasapi.controllers;

import com.uvas.uvasapi.controllers.dtos.EmailCreateOrUpdateDTO;
import com.uvas.uvasapi.controllers.dtos.GrupoCreateOrUpdateDTO;
import com.uvas.uvasapi.controllers.dtos.PessoaCreateOrUpdateDTO;
import com.uvas.uvasapi.controllers.dtos.PhoneCreateOrUpdateDTO;
import com.uvas.uvasapi.domain.Email;
import com.uvas.uvasapi.domain.Grupo;
import com.uvas.uvasapi.domain.Pessoa;
import com.uvas.uvasapi.domain.Phone;
import com.uvas.uvasapi.services.EmailService;
import com.uvas.uvasapi.services.GrupoService;
import com.uvas.uvasapi.services.PessoaService;
import com.uvas.uvasapi.services.PhoneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        pessoaService.updatePessoa(pessoa);

        if (dto.getPhones() != null) {
            for (PhoneCreateOrUpdateDTO phoneDto : dto.getPhones()) {
                Phone phone = phoneDto.getPhone();
                phone.setPessoaId(pessoa);
                phoneService.createPhone(phone);
            }
        }
        if (dto.getEmails() != null) {
            for (EmailCreateOrUpdateDTO emailDto : dto.getEmails()) {
                Email email = emailDto.getEmail();
                email.setPessoaId(pessoa);
                //Check if email already exists
                Email existingEmail = emailService.getEmailByEmail(email.getEmail());
                if (existingEmail != null) {
                    existingEmail.setPessoaId(pessoa);
                    emailService.updateEmail(existingEmail);
                } else {
                    emailService.createEmail(email);
                }
            }
        }
        if (dto.getGrupos() != null) {
            for (GrupoCreateOrUpdateDTO grupoDto : dto.getGrupos()) {
                Grupo grupo = grupoDto.getGrupo();
                grupo.getIntegrantes().add(pessoa);
                grupoService.updateGrupo(grupo);
            }
        }

        return ResponseEntity.status(200).body(pessoa);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Pessoa> deletePessoa(@PathVariable String id){
        pessoaService.deletePessoa(id);

        return ResponseEntity.noContent().build();
    }

}
