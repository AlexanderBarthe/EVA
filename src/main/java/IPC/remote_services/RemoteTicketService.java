package IPC.remote_services;

import IPC.TCPClient;
import interfaces.TicketServiceInterface;
import models.Customer;
import models.Event;
import models.Ticket;

import java.util.List;

public class RemoteTicketService implements TicketServiceInterface {

    private final TCPClient tcpClient;

    public RemoteTicketService(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public Ticket createTicket(Customer customer, Event event) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Ticket getTicketById(long id) {
        return null;
    }

    @Override
    public List<Ticket> getAllTickets() {
        return List.of();
    }

    @Override
    public void deleteTicket(long id) throws IllegalArgumentException {

    }

    @Override
    public void deleteAllTickets() {

    }

    @Override
    public boolean verifyTicket(long ticketId, long customerId, long eventId) {
        return false;
    }
}
