 package com.uvas.uvasapi.domain;

import com.uvas.uvasapi.domain.enums.EmailType;
import com.uvas.uvasapi.domain.enums.PhoneType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

 @Entity
 @Table(name = "email")
 @Data
 public class Email {

     @Id
     @GeneratedValue(strategy = GenerationType.UUID)
     private String id;

     @Enumerated(EnumType.STRING)
     @Column(nullable = false)
     private EmailType emailType;

     @Column(length = 200)
     private String email;

     @ManyToOne
     @JoinColumn(name = "pessoa_id")
     private Pessoa pessoaId;

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
