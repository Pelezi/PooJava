 package com.uvas.uvasapi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uvas.uvasapi.domain.enums.PhoneType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "phone")
@Data
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PhoneType PhoneType;

    @Column(length = 20, unique = true)
    private String numero;

    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    @JsonBackReference(value = "pessoa-phones")
    private Pessoa pessoaId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = updatedAt = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDate.now();
    }
}
