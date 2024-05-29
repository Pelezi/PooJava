package com.uvas.uvasapi.controllers.dtos;

import com.uvas.uvasapi.domain.Celula;
import com.uvas.uvasapi.domain.Lider;
import com.uvas.uvasapi.domain.Pessoa;
import com.uvas.uvasapi.services.PessoaService;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LiderCreateOrUpdateDTO {

    @NotNull(message = "Pessoa não informada")
    private Pessoa pessoaId;

    private List<Celula> celulas;

    public Lider getLider(PessoaService pessoaService){
        Lider lider = new Lider();

        Pessoa pessoa = pessoaService.getPessoaById(pessoaId.getId());
        lider.setPessoaId(pessoa);

        return lider;
    }

}
