package com.uvas.uvasapi.domain;

import com.uvas.uvasapi.domain.enums.Cargo;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "pessoa")
@Data
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 200, nullable = false)
    private String nome;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_batismo")
    private LocalDate dataBatismo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cargo cargo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    @PrePersist
    public void prePersist() {
        createdAt = updatedAt = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDate.now();
    }
}
