package controllers;

import interfaces.CustomerServiceInterface;
import interfaces.EventServiceInterface;
import interfaces.TicketServiceInterface;
import logging.LogService;
import services.CustomerService;
import services.EventService;
import services.TicketService;

public class TicketShop {

    private LogService logService;

    private CustomerServiceInterface customerService;
    private EventServiceInterface eventService;
    private TicketServiceInterface ticketService;

    public TicketShop() {
        this.logService = new LogService();

        this.customerService = new CustomerService(logService);
        this.eventService = new EventService(logService);
        this.ticketService = new TicketService(eventService, customerService, logService);
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

    public LogService getLogService() {
        return logService;
    }

}
