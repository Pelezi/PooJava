package com.uvas.uvasapi.controllers.dtos;

import com.uvas.uvasapi.domain.Email;
import com.uvas.uvasapi.domain.enums.EmailType;
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

    public Email getEmail(){
        Email email = new Email();

        email.setEmail(this.email);
        email.setEmailType(emailType);

        return email;
    }

}
