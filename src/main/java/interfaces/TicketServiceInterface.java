package interfaces;

import models.Customer;
import models.Event;
import models.Ticket;

public interface TicketServiceInterface {
    Ticket createTicket(Customer customer, Event event) throws IllegalArgumentException;
    Ticket getTicketById(long id);
    Iterable<Ticket> getAllTickets();
    void deleteTicket(long id) throws IllegalArgumentException;
    void deleteAllTickets();
    boolean verifyTicket(long ticketId, long customerId, long eventId);
}
