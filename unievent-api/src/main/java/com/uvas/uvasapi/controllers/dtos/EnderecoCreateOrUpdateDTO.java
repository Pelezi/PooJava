package com.uvas.uvasapi.controllers.dtos;

import com.uvas.uvasapi.domain.Endereco;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class EnderecoCreateOrUpdateDTO {

    @NotBlank(message = "Bairro não informado")
    @Length(max = 200, message = "Bairro não pode exceder 200 caracteres")
    private String bairro;

    private String rua;

    private String numero;

    public Endereco getEndereco(){
        Endereco endereco = new Endereco();

        endereco.setBairro(bairro);
        endereco.setRua(rua);
        endereco.setNumero(numero);

        return endereco;
    }

}
