package com.uvas.uvasapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "celulas")
@Data
public class Celula {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 200, nullable = false)
    private String nome;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_inauguracao")
    private LocalDate dataInauguracao;
    
    @ManyToOne
    @JoinColumn(name = "discipulador_id")
    @JsonIgnoreProperties({"celulas", "createdAt", "updatedAt"})
    private Discipulador discipuladorId;
    
    @ManyToOne
    @JoinColumn(name = "lider_id")
    @JsonIgnoreProperties({"celulas", "createdAt", "updatedAt"})
    private Lider liderId;

    @OneToMany(mappedBy = "celulaId", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"celulaId", "phones", "emails", "grupos", "createdAt", "updatedAt", "enderecoId"})
    private List<Pessoa> pessoas;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "endereco_id")
    private Endereco enderecoId;

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
