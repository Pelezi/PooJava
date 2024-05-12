package com.uvas.uvasapi.controllers.dtos;

import com.uvas.uvasapi.domain.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DiretorCreateOrUpdateDTO {
    
    @NotNull(message = "Pessoa não informada")
    private Pessoa pessoaId;
    
    private List<GrupoCreateOrUpdateDTO> grupos;

    public Diretor getDiretor(){
        Diretor diretor = new Diretor();
        
        diretor.setPessoaId(pessoaId);
        diretor.setGrupos(grupos.stream().map(GrupoCreateOrUpdateDTO::getGrupo).toList());

        return diretor;
    }

}
