package com.uvas.uvasapi.repositories;

import com.uvas.uvasapi.domain.Celula;
import com.uvas.uvasapi.domain.enums.DiaDaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface CelulaRepository extends JpaRepository<Celula, String> {
    //find a list of celulas by diaDaSemana
    List<Celula> findByDiaDaSemana(DiaDaSemana diaDaSemana);
    //find a list of celulas by horário
    List<Celula> findByHorario(LocalTime horario);
    //find a list of celulas by enderecoId.bairro
    List<Celula> findByEnderecoIdBairro(String bairro);
    //find a list of celulas by liderId.id
    List<Celula> findByLiderIdId(String liderId);
    //find a list of celulas by discipuladorId.id
    List<Celula> findByDiscipuladorIdId(String discipuladorId);
}
