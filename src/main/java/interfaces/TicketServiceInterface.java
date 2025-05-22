package interfaces;

import models.Customer;
import models.Event;
import models.Ticket;

import java.util.List;

public interface TicketServiceInterface {
    Ticket createTicket(Customer customer, Event event) throws IllegalArgumentException;
    Ticket getTicketById(long id);
    List<Ticket> getAllTickets();
    void deleteTicket(long id) throws IllegalArgumentException;
    void deleteAllTickets();
    boolean verifyTicket(long ticketId, long customerId, long eventId);
}
