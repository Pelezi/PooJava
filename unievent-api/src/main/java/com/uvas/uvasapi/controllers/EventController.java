package com.uvas.uvasapi.controllers;

import com.uvas.uvasapi.controllers.dtos.EventCreateOrUpdateDTO;
import com.uvas.uvasapi.domain.Event;
import com.uvas.uvasapi.services.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "events")
@CrossOrigin
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getEvents(){
        List<Event> events = eventService.getEvents();

        return ResponseEntity.ok(events);
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody @Valid EventCreateOrUpdateDTO dto){
        Event event = eventService.createEvent(dto.getEvent());

        return ResponseEntity.status(201).body(event);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Event> getEventById(@PathVariable String id){
        Event event = eventService.getEventById(id);

        return ResponseEntity.ok(event);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable String id, @RequestBody @Valid EventCreateOrUpdateDTO dto){
        Event event = dto.getEvent();
        event.setId(id);
        eventService.updateEvent(event);

        return ResponseEntity.status(200).body(event);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Event> deleteEvent(@PathVariable String id){
        eventService.deleteEvent(id);

        return ResponseEntity.noContent().build();
    }

}
