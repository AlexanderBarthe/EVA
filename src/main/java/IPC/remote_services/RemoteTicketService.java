package IPC.remote_services;

import IPC.TCPClient;
import interfaces.TicketServiceInterface;
import models.Customer;
import models.Event;
import models.Ticket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RemoteTicketService implements TicketServiceInterface {

    private final TCPClient tcpClient;

    public RemoteTicketService(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
    }

    @Override
    public Ticket createTicket(Customer customer, Event event) throws IllegalArgumentException {
        try {
            String response = tcpClient.send("ticket;create;" + customer.getId() + "," + event.getId());
            return Ticket.fromString(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ticket getTicketById(long id) {
        return null;
    }

    @Override
    public List<Ticket> getAllTickets() {
        try {
            String response = tcpClient.send("ticket;getall;");
            String[] ticketStrings =  response.split(";");
            List<Ticket> tickets = new ArrayList<>();
            for (String ticketString : ticketStrings) {
                tickets.add(Ticket.fromString(ticketString));
            }
            return tickets;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
