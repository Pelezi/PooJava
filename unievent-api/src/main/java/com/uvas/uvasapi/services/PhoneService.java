package com.uvas.uvasapi.services;

import com.uvas.uvasapi.domain.Phone;
import com.uvas.uvasapi.exceptions.NotFoundException;
import com.uvas.uvasapi.repositories.PhoneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    public List<Phone> getPhones(){
        return phoneRepository.findAll();
    }

    @Transactional(rollbackOn = Exception.class)
    public Phone createPhone(Phone phone) {

        phoneRepository.save(phone);
        return phone;
    }

    public Phone getPhoneById(String id) {
        Optional<Phone> phoneExists = phoneRepository.findById(id);

        return phoneExists.orElseThrow(() -> new NotFoundException("Número não encontrado"));
    }

    @Transactional(rollbackOn = Exception.class)
    public Phone updatePhone(Phone phone) {

        phoneRepository.save(phone);
        return phone;
    }

    public void deletePhone(String id) {
        Optional<Phone> phoneExists = phoneRepository.findById(id);

        Phone phone = phoneExists.orElseThrow(() -> new NotFoundException("Número não encontrado"));
        phoneRepository.delete(phone);
    }

}
