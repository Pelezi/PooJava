package com.uvas.uvasapi.controllers.dtos;

import com.uvas.uvasapi.domain.Endereco;
import com.uvas.uvasapi.domain.enums.AddressType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Tipo de endereço não informado")
    private AddressType addressType;

    public Endereco getEndereco(){
        Endereco endereco = new Endereco();

        endereco.setBairro(bairro);
        endereco.setRua(rua);
        endereco.setNumero(numero);
        endereco.setAddressType(addressType);

        return endereco;
    }

}
