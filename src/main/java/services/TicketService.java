package services;

import com.sun.jdi.InternalException;
import models.Customer;
import models.Event;
import models.Ticket;

import java.time.LocalDate;
import java.util.HashMap;

public class TicketService {

    private HashMap<Long, Ticket> ticketsById;
    private IDService idService;
    private EventService eventService;
    private CustomerService customerService;

    public TicketService() {
        this.ticketsById = new HashMap<>();
        this.idService = new IDService();
        this.eventService = new EventService();
        this.customerService = new CustomerService();
    }

    public Ticket createTicket(Customer customer, Event event) {
        long generatedId = idService.generateNewId();

        if(generatedId == -1) {
            throw new InternalException("An error occured while creating a new id");
        }

        LocalDate transactiondate = LocalDate.now();

        if(event.getTicketsAvailable()==0) {
            throw new IllegalArgumentException("There are no tickets available for this event");
        }

        int ticketsForEvent = 0;
        for(Ticket ticket: customer.getTickets()){
            if(ticket.getEvent().equals(event)){
                ticketsForEvent++;
            }
        }

        if(ticketsForEvent >= 5) {
            throw new IllegalArgumentException("Customer already has 5 tickets for the event");
        }


        Ticket ticket = new Ticket(generatedId, transactiondate, customer, event);
        event.setTicketsAvailable(event.getTicketsAvailable() - 1);
        event.addTicket(ticket);
        customer.addTicket(ticket);


        return ticket;
    }
}
