package controllers;

import interfaces.CustomerServiceInterface;
import interfaces.EventServiceInterface;
import interfaces.TicketServiceInterface;
import services.CustomerService;
import services.EventService;
import services.TicketService;

public class TicketShop {

    private CustomerServiceInterface customerService;
    private EventServiceInterface eventService;
    private TicketServiceInterface ticketService;

    public TicketShop() {
        this.customerService = new CustomerService();
        this.eventService = new EventService();
        this.ticketService = new TicketService(eventService, customerService);
    }

    public CustomerServiceInterface getCustomerService() {
        return customerService;
    }

    public EventServiceInterface getEventService() {
        return eventService;
    }

    public TicketServiceInterface getTicketService() {
        return ticketService;
    }


}
