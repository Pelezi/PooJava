package com.unievent.unieventapi.repositories;

import com.unievent.unieventapi.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {

    public Optional<Event> findEventByTitle(String title);

}
