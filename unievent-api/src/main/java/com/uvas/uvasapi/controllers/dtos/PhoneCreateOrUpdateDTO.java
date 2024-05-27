package com.uvas.uvasapi.controllers.dtos;

import com.uvas.uvasapi.domain.Pessoa;
import com.uvas.uvasapi.domain.Phone;
import com.uvas.uvasapi.domain.enums.PhoneType;
import com.uvas.uvasapi.services.PessoaService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class PhoneCreateOrUpdateDTO {

    @NotBlank(message = "Número não informado")
    @Length(max = 200, message = "Número não pode exceder 20 caracteres")
    private String numero;

    @NotNull(message = "Tipo de número não informado")
    private PhoneType phoneType;

    @NotNull(message = "Pessoa não informada")
    private String pessoaId;

    public Phone getPhone(PessoaService pessoaService){
        Phone phone = new Phone();

        Pessoa pessoa = pessoaService.getPessoaById(pessoaId);

        phone.setNumero(numero);
        phone.setPhoneType(phoneType);
        phone.setPessoaId(pessoa);

        return phone;
    }

}
