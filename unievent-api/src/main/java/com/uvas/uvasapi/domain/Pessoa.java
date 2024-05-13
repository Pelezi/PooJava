package com.uvas.uvasapi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.uvas.uvasapi.domain.enums.Cargo;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pessoa")
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "endereco_id")
    private Endereco enderecoId;

    @OneToMany(mappedBy = "pessoaId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference(value = "pessoa-phones")
    private List<Phone> phones;

    @OneToMany(mappedBy = "pessoaId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference(value = "pessoa-emails")
    private List<Email> emails;

    @ManyToOne
    @JoinColumn(name = "celula_id")
    @JsonBackReference(value = "celula-integrantes")
    private Celula celulaId;

    @ManyToMany(mappedBy = "integrantes")
    private List<Grupo> grupos;

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
