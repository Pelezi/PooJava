package com.uvas.uvasapi.controllers.dtos;

import com.uvas.uvasapi.domain.Discipulador;
import com.uvas.uvasapi.domain.Pessoa;
import com.uvas.uvasapi.domain.enums.Rede;
import com.uvas.uvasapi.services.PessoaService;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DiscipuladorCreateOrUpdateDTO {
    
    @NotNull(message = "Pessoa não informada")
    private String pessoaId;

    private Rede rede;

    public Discipulador getDiscipulador(PessoaService pessoaService){
        Discipulador discipulador = new Discipulador();

        Pessoa pessoa = pessoaService.getPessoaById(pessoaId);

        discipulador.setPessoaId(pessoa);
        discipulador.setRede(rede);

        return discipulador;
    }

}
