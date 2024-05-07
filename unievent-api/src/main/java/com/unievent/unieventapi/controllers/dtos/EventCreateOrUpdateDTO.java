package com.unievent.unieventapi.controllers.dtos;

import com.unievent.unieventapi.domain.Event;
import com.unievent.unieventapi.services.EventService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EventCreateOrUpdateDTO {

    @NotBlank(message = "Título do evento não informado")
    @Length(max = 200, message = "Título do evento não pode exceder 200 caracteres")
    private String title;

    @Length(max = 500, message = "Descrição do evento não pode exceder 500 caracteres")
    private String description;

    @NotNull(message = "Data de início do evento não informada")
    private LocalDate startDate;

    @NotNull(message = "Data de fim do evento não informada")
    private LocalDate endDate;

    @Min(value = 10, message = "O evento deve ter pelo menos 10 vagas")
    private int vacancies;

    public Event getEvent(){
        Event event = new Event();

        event.setTitle(title);
        event.setDescription(description);
        event.setStartDate(startDate);
        event.setEndDate(endDate);
        event.setVacancies(vacancies);

        return event;
    }

}
