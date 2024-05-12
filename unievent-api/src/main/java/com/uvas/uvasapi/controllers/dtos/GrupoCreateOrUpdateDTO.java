package com.uvas.uvasapi.controllers.dtos;

import com.uvas.uvasapi.domain.Diretor;
import com.uvas.uvasapi.domain.Endereco;
import com.uvas.uvasapi.domain.Grupo;
import com.uvas.uvasapi.domain.Pessoa;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class GrupoCreateOrUpdateDTO {

    @NotNull(message = "Nome do grupo não informado")
    private String nome;

    private LocalDate dataInauguracao;

    @NotNull(message = "Diretor do grupo não informado")
    private Diretor diretorId;

    private List<PessoaCreateOrUpdateDTO> integrantes;

    private Endereco enderecoId;

    public Grupo getGrupo(){
        Grupo grupo = new Grupo();
        
        grupo.setNome(nome);
        grupo.setDataInauguracao(dataInauguracao);
        grupo.setDiretorId(diretorId);
        grupo.setIntegrantes(integrantes.stream().map(PessoaCreateOrUpdateDTO::getPessoa).toList());
        grupo.setEnderecoId(enderecoId);

        return grupo;
    }

}
