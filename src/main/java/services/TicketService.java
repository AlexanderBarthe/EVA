package services;

import com.sun.jdi.InternalException;
import interfaces.CustomerServiceInterface;
import interfaces.EventServiceInterface;
import interfaces.TicketServiceInterface;
import logging.CreateTicketEvent;
import logging.LogService;
import models.Customer;
import models.Event;
import models.Ticket;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TicketService implements TicketServiceInterface {

    private Map<Long, Ticket> ticketsById = new ConcurrentHashMap<>();
    private final IDService idService;
    private final EventServiceInterface eventService;
    private final CustomerServiceInterface customerService;

    private LogService logService;

    public TicketService(EventServiceInterface eventService, CustomerServiceInterface customerService, LogService logService) {
        this.idService = new IDService();
        this.eventService = eventService;
        this.customerService = customerService;

        this.logService = logService;
    }

    public synchronized Ticket createTicket(Customer customer, Event event) throws IllegalArgumentException, InternalException {
        long generatedId = idService.generateNewId();

        if(generatedId == -1) {
            throw new InternalException("An error occured while creating a new id");
        }

        LocalDate transactiondate = LocalDate.now();

        if(event.getTicketsAvailable().get()<=0) {
            throw new IllegalArgumentException("There are no tickets available for this event");
        }

        int ticketsForEvent;
        ticketsForEvent = customer.getTicketPerEvent().getOrDefault(event.getId(), 0);

        if(ticketsForEvent >= 5) {
            throw new IllegalArgumentException("Customer already has 5 tickets for the event");
        }


        Ticket ticket = new Ticket(generatedId, transactiondate, customer, event);
        ticketsById.put(generatedId, ticket);
        event.addTicket(ticket);
        customer.addTicket(ticket);
        customer.setTicketPerEvent(event.getId(), ticketsForEvent + 1);
        event.getTicketsAvailable().decrementAndGet();

        customerService.updateCustomer(customer);
        eventService.updateEvent(event);

        logService.log(new CreateTicketEvent(ticket));

        return ticket;
    }

    public Ticket getTicketById(long id) {
        if(!ticketsById.containsKey(id)) {
            return null;
        }
        return new Ticket(ticketsById.get(id));
    }

    public List<Ticket> getAllTickets() {
        return new ArrayList<>(ticketsById.values());
    }


    public void deleteTicket(long id) throws IllegalArgumentException{

        if(!ticketsById.containsKey(id)) {
            throw new IllegalArgumentException("Ticket with id " + id + " does not exist.");
        }

        idService.removeId(id);
        ticketsById.remove(id);
    }

    public void deleteAllTickets() {
        ticketsById.clear();
        idService.dropAllIds();
    }

    public boolean verifyTicket(long ticketId, long customerId, long eventId) throws IllegalArgumentException{
        Ticket ticket = ticketsById.get(ticketId);
        long actualCustomerId = ticket.getCustomer().getId();
        long actualEventId = ticket.getEvent().getId();

        if(!ticketsById.containsKey(ticketId)){
            throw new IllegalArgumentException("ticket does not exist");
        }
        if (idService.containsId(customerId)){
            throw new IllegalArgumentException("customer does not exist");
        }
        if(idService.containsId(eventId)){
            throw new IllegalArgumentException("event does not exist");
        }


        return actualCustomerId == customerId && actualEventId == eventId;
    }
}
