package com.uvas.uvasapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.uvas.uvasapi.domain.enums.DiaDaSemana;
import jakarta.persistence.*;
import lombok.Data;

import java.time.*;
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

    @Enumerated(EnumType.STRING)
    @Column()
    private DiaDaSemana diaDaSemana;

    @Temporal(TemporalType.TIME)
    @Column()
    private LocalTime horario;

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
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
