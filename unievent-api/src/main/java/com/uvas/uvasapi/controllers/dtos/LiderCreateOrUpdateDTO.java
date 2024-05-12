package com.uvas.uvasapi.controllers.dtos;

import com.uvas.uvasapi.domain.Lider;
import com.uvas.uvasapi.domain.Pessoa;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LiderCreateOrUpdateDTO {

    @NotNull(message = "Pessoa não informada")
    private Pessoa pessoaId;

    private List<CelulaCreateOrUpdateDTO> celulas;

    public Lider getLider(){
        Lider lider = new Lider();

        lider.setPessoaId(pessoaId);
        lider.setCelulas(celulas.stream().map(CelulaCreateOrUpdateDTO::getCelula).toList());

        return lider;
    }

}
