package com.uvas.uvasapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.uvas.uvasapi.domain.enums.Rede;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "discipulador")
@Data
public class Discipulador {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "pessoa_id")
    @JsonIgnoreProperties({"celulaId", "grupos", "createdAt", "updatedAt"})
    private Pessoa pessoaId;

    @OneToMany(mappedBy = "discipuladorId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"discipuladorId", "pessoas", "createdAt", "updatedAt"})
    private List<Celula> celulas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rede rede;

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
