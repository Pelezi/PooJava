package com.uvas.uvasapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "lider")
@Data
public class Lider {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pessoa_id")
    @JsonIgnoreProperties({"grupos", "celulaId", "createdAt", "updatedAt"})
    private Pessoa pessoaId;
    
    @OneToMany(mappedBy = "liderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"liderId", "createdAt", "updatedAt"})
    private List<Celula> celulas;
    
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
