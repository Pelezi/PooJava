package com.uvas.uvasapi.controllers.dtos;

import com.uvas.uvasapi.domain.*;
import com.uvas.uvasapi.services.GrupoService;
import com.uvas.uvasapi.services.PessoaService;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class DiretorCreateOrUpdateDTO {

    @NotNull(message = "Pessoa não informada")
    private Pessoa pessoaId;

    private List<Grupo> grupos;

    public Diretor getDiretor(PessoaService pessoaService){
        Diretor diretor = new Diretor();

        Pessoa pessoa = pessoaService.getPessoaById(pessoaId.getId());
        diretor.setPessoaId(pessoa);

        return diretor;
    }

}
