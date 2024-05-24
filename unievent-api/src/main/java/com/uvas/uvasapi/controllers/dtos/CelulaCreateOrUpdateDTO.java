package com.uvas.uvasapi.controllers.dtos;

import com.uvas.uvasapi.domain.*;
import com.uvas.uvasapi.services.DiscipuladorService;
import com.uvas.uvasapi.services.PessoaService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CelulaCreateOrUpdateDTO {

    @NotBlank(message = "Nome da celula não informado")
    @Length(max = 200, message = "Nome da celula não pode exceder 200 caracteres")
    private String nome;

    private LocalDate dataInauguracao;

    private String discipuladorId;

    private Lider liderId;

    private List<Pessoa> pessoas = new ArrayList<>();

    private Endereco enderecoId;

    public Celula getCelula(DiscipuladorService discipuladorService){
        Celula celula = new Celula();

        Discipulador discipulador = discipuladorService.getDiscipuladorById(discipuladorId);

        celula.setNome(nome);
        celula.setDataInauguracao(dataInauguracao);
        celula.setDiscipuladorId(discipulador);
        celula.setLiderId(liderId);
        celula.setEnderecoId(enderecoId);

        return celula;
    }

}
