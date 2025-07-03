package com.tickets;

import IPC.TCPClient;
import IPC.remote_services.RemoteCustomerService;
import IPC.remote_services.RemoteEventService;
import IPC.remote_services.RemoteTicketService;
import interfaces.CustomerServiceInterface;
import interfaces.EventServiceInterface;
import interfaces.TicketServiceInterface;
import logging.LogService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import services.CustomerService;
import services.EventService;
import services.TicketService;

@Component
public class TicketShop {

    private LogService logService;

    private static CustomerServiceInterface customerService;
    private static EventServiceInterface eventService;
    private static TicketServiceInterface ticketService;

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
