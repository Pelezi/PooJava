package com.uvas.uvasapi.controllers.dtos;

import com.uvas.uvasapi.domain.Discipulador;
import com.uvas.uvasapi.domain.Pessoa;
import com.uvas.uvasapi.domain.enums.Rede;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DiscipuladorCreateOrUpdateDTO {
    
    @NotNull(message = "Pessoa não informada")
    private Pessoa pessoaId;
    
    private List<CelulaCreateOrUpdateDTO> celulas;

    private Rede rede;

    public Discipulador getDiscipulador(){
        Discipulador discipulador = new Discipulador();

        discipulador.setPessoaId(pessoaId);
        discipulador.setCelulas(celulas.stream().map(CelulaCreateOrUpdateDTO::getCelula).toList());
        discipulador.setRede(rede);

        return discipulador;
    }

}
