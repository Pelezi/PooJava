package com.uvas.uvasapi.controllers.dtos;

import com.uvas.uvasapi.domain.Email;
import com.uvas.uvasapi.domain.Pessoa;
import com.uvas.uvasapi.domain.enums.EmailType;
import com.uvas.uvasapi.services.PessoaService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class EmailCreateOrUpdateDTO {

    @NotBlank(message = "Email não informado")
    @Length(max = 200, message = "Email não pode exceder 200 caracteres")
    private String email;

    @NotNull(message = "Tipo de email não informado")
    private EmailType emailType;

    @NotNull(message = "Pessoa não informada")
    private String pessoaId;

    public Email getEmail(PessoaService pessoaService){
        Email email = new Email();

        Pessoa pessoa = pessoaService.getPessoaById(pessoaId);

        email.setEmail(this.email);
        email.setEmailType(emailType);
        email.setPessoaId(pessoa);

        return email;
    }

}
