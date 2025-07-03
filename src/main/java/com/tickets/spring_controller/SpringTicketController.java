package com.tickets.spring_controller;


import com.tickets.TicketShop;
import models.Ticket;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class SpringTicketController {

    private TicketShop ticketShop;

    public SpringTicketController() {
        this.ticketShop = new TicketShop();
    }

    @GetMapping("/getAll")
    public List<Ticket> getAllTickets() {
        return ticketShop.getTicketService().getAllTickets();
    }
}
