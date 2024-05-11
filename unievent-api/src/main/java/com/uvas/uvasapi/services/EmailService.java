package com.uvas.uvasapi.services;

import com.uvas.uvasapi.domain.Email;
import com.uvas.uvasapi.exceptions.NotFoundException;
import com.uvas.uvasapi.repositories.EmailRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    public List<Email> getEmails(){
        return emailRepository.findAll();
    }

    @Transactional(rollbackOn = Exception.class)
    public Email createEmail(Email email) {

        emailRepository.save(email);
        return email;
    }

    public Email getEmailById(String id) {
        Optional<Email> emailExists = emailRepository.findById(id);

        return emailExists.orElseThrow(() -> new NotFoundException("Email não encontrado"));
    }

    @Transactional(rollbackOn = Exception.class)
    public Email updateEmail(Email email) {

        emailRepository.save(email);
        return email;
    }

    public void deleteEmail(String id) {
        Optional<Email> emailExists = emailRepository.findById(id);

        Email email = emailExists.orElseThrow(() -> new NotFoundException("Email não encontrado"));
        emailRepository.delete(email);
    }

}
