package com.unievent.unieventapi.services;

import com.unievent.unieventapi.domain.Event;
import com.unievent.unieventapi.exceptions.BusinessException;
import com.unievent.unieventapi.exceptions.NotFoundException;
import com.unievent.unieventapi.repositories.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getEvents(){
        return eventRepository.findAll();
    }

    @Transactional(rollbackOn = Exception.class)
    public Event createEvent(Event event) {
        Optional<Event> eventExists = eventRepository.findEventByTitle(event.getTitle());
        if(eventExists.isPresent()){
            throw new BusinessException("Já existe um evento com o título informado");
        }

        eventRepository.save(event);
        return event;
    }

    public Event getEventById(String id) {
        Optional<Event> eventExists = eventRepository.findById(id);

        return eventExists.orElseThrow(() -> new NotFoundException("Evento não encontrado"));
    }

    @Transactional(rollbackOn = Exception.class)
    public Event updateEvent(Event event) {
        Optional<Event> eventExists = eventRepository.findEventByTitle(event.getTitle());
        if(eventExists.isPresent() && !eventExists.get().getId().equals(event.getId())){
            throw new BusinessException("Já existe um evento com o título informado");
        }

        eventRepository.save(event);
        return event;
    }

    public void deleteEvent(String id) {
        Optional<Event> eventExists = eventRepository.findById(id);

        Event event = eventExists.orElseThrow(() -> new NotFoundException("Evento não encontrado"));
        eventRepository.delete(event);
    }

}
