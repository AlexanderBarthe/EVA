package services;

import com.sun.jdi.InternalException;
import interfaces.TicketServiceInterface;
import models.Customer;
import models.Event;
import models.Ticket;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

public class TicketService implements TicketServiceInterface {

    private static HashMap<Long, Ticket> ticketsById = new HashMap<>();
    private IDService idService;
    private EventService eventService;
    private CustomerService customerService;

    public TicketService() {
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
        ticketsById.put(generatedId, ticket);
        event.addTicket(ticket);
        customer.addTicket(ticket);
        event.setTicketsAvailable(event.getTicketsAvailable() - 1);


        return ticket;
    }

    public Ticket getTicketById(long id) {
        if(!ticketsById.containsKey(id)) {
            return null;
        }
        return new Ticket(ticketsById.get(id));
    }

    public Collection<Ticket> getAllTickets() {
        return ticketsById.values();
    }


    public void deleteTicket(long id){

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

    public boolean verifyTicket(long ticketId, long customerId, long eventId){
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
