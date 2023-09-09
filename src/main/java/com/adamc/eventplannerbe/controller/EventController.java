package com.adamc.eventplannerbe.controller;

import com.adamc.eventplannerbe.entities.Event;
import com.adamc.eventplannerbe.service.EventService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("events")
public class EventController {
    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("")
    public ArrayList<Event> getEventsByQuery(@RequestParam(required = false) String query) {
        return eventService.getAllByTitleOrCreator(query);
    }

    @PostMapping("/create")
    public String postEvent(@RequestBody Event event) {
        if(Objects.isNull(event)) {
            return "Null";
        }

        if(event.getTitle().isEmpty()) {
            return "No title";
        }

        if(event.getCreator().isEmpty()) {
            return "No creator";
        }


        if(eventService.postEvent(event)) {
            return "Created event " + event.getTitle() + " " + event.getId();
        } else {
            return "Failed";
        }


    }
}
