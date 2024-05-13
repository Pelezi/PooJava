package com.uvas.uvasapi.repositories;

import com.uvas.uvasapi.domain.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, String> {
    public Optional<Phone> findByNumero(String numero);

}
