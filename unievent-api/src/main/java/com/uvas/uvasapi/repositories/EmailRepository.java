package com.uvas.uvasapi.repositories;

import com.uvas.uvasapi.domain.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, String> {
    public Optional<Email> findByEmail(String email);

}
