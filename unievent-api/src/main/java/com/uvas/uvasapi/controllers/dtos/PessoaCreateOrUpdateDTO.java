package com.uvas.uvasapi.controllers.dtos;

import com.uvas.uvasapi.domain.Endereco;
import com.uvas.uvasapi.domain.Pessoa;
import com.uvas.uvasapi.domain.enums.Cargo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PessoaCreateOrUpdateDTO {

    @NotBlank(message = "Nome da pessoa não informado")
    @Length(max = 200, message = "Nome da pessoa não pode exceder 200 caracteres")
    private String nome;

    private LocalDate dataNascimento;

    private LocalDate dataBatismo;

    @NotNull(message = "Cargo da pessoa não informado")
    private Cargo cargo;

    private Endereco enderecoId;

    public Pessoa getPessoa(){
        Pessoa pessoa = new Pessoa();

        pessoa.setNome(nome);
        pessoa.setDataNascimento(dataNascimento);
        pessoa.setDataBatismo(dataBatismo);
        pessoa.setCargo(cargo);
        pessoa.setEnderecoId(enderecoId);

        return pessoa;
    }

}
