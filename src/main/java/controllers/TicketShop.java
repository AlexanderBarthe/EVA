package controllers;

import IPC.TCPClient;
import IPC.remote_services.RemoteCustomerService;
import IPC.remote_services.RemoteEventService;
import IPC.remote_services.RemoteTicketService;
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

    private TCPClient tcpClient;

    public TicketShop() {
        this.logService = new LogService(true);

        this.customerService = new CustomerService(logService);
        this.eventService = new EventService(logService);
        this.ticketService = new TicketService(eventService, customerService, logService);

    }

    public TicketShop(TCPClient tcpClient) {
        this.tcpClient = tcpClient;
        this.logService = new LogService(true);

        this.customerService = new RemoteCustomerService(tcpClient);
        this.eventService = new RemoteEventService(tcpClient);
        this.ticketService = new RemoteTicketService(tcpClient);
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
