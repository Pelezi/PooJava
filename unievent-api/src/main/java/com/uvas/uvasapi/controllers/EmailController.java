package com.uvas.uvasapi.controllers;

import com.uvas.uvasapi.controllers.dtos.EmailCreateOrUpdateDTO;
import com.uvas.uvasapi.domain.Email;
import com.uvas.uvasapi.services.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "emails")
@CrossOrigin
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping
    public ResponseEntity<List<Email>> getEmails(){
        List<Email> emails = emailService.getEmails();

        return ResponseEntity.ok(emails);
    }

    @PostMapping
    public ResponseEntity<Email> createEmail(@RequestBody @Valid EmailCreateOrUpdateDTO dto){
        Email email = emailService.createEmail(dto.getEmail());

        return ResponseEntity.status(201).body(email);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Email> getEmailById(@PathVariable String id){
        Email email = emailService.getEmailById(id);

        return ResponseEntity.ok(email);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Email> updateEmail(@PathVariable String id, @RequestBody @Valid EmailCreateOrUpdateDTO dto){
        Email email = dto.getEmail();
        email.setId(id);
        emailService.updateEmail(email);

        return ResponseEntity.status(200).body(email);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Email> deleteEmail(@PathVariable String id){
        emailService.deleteEmail(id);

        return ResponseEntity.noContent().build();
    }

}
