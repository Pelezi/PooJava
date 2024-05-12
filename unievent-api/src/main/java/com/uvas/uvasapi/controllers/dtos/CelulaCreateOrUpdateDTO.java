package com.uvas.uvasapi.controllers.dtos;

import com.uvas.uvasapi.domain.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class CelulaCreateOrUpdateDTO {

    @NotBlank(message = "Nome da celula não informado")
    @Length(max = 200, message = "Nome da celula não pode exceder 200 caracteres")
    private String nome;

    private LocalDate dataInauguracao;

    private Discipulador discipuladorId;

    private Lider liderId;

    private List<PessoaCreateOrUpdateDTO> pessoas;

    private Endereco enderecoId;

    public Celula getCelula(){
        Celula celula = new Celula();

        celula.setNome(nome);
        celula.setDataInauguracao(dataInauguracao);
        celula.setDiscipuladorId(discipuladorId);
        celula.setLiderId(liderId);
        celula.setPessoas(pessoas.stream().map(PessoaCreateOrUpdateDTO::getPessoa).toList());
        celula.setEnderecoId(enderecoId);

        return celula;
    }

}
