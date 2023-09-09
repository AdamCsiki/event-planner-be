package com.adamc.eventplannerbe.service;

import com.adamc.eventplannerbe.entities.Event;
import com.adamc.eventplannerbe.repos.EventRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public ArrayList<Event> getAllByTitleOrCreator(String query) {
        ArrayList<Event> events = new ArrayList<>();

        if(Objects.isNull(query) || query.isEmpty()) {
            events.addAll(eventRepository.findAll());
        } else {
            events.addAll(eventRepository.findAllByTitleOrCreator(query, query));
        }
        
        return events;
    }

    public boolean postEvent(Event event) {
        Event check = eventRepository.save(event);

        return !Objects.isNull(check);
    }
}
