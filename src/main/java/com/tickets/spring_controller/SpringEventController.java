package com.tickets.spring_controller;


import com.tickets.TicketShop;
import models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class SpringEventController {

    private TicketShop ticketShop;
    @Autowired
    public SpringEventController(TicketShop ticketShop) {
        this.ticketShop = ticketShop;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return ticketShop.getEventService().getAllEvents();
    }

    @PostMapping
    public Event createEvent(@RequestBody Event newEvent) {
        return ticketShop.getEventService().createEvent(newEvent.getName(), newEvent.getLocation(), newEvent.getTime(), newEvent.getTicketsAvailable().get());
    }

}

