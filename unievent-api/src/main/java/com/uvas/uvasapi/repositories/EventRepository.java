package com.uvas.uvasapi.repositories;

import com.uvas.uvasapi.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

    public Optional<Event> findEventByTitle(String title);

}
