package logging.events;

import logging.LoggableEvent;
import models.Ticket;

import java.time.LocalDateTime;

public class CreateTicketEvent implements LoggableEvent {

    private Ticket ticket;
    private LocalDateTime eventTime;
    private String threadString;

    public CreateTicketEvent(Ticket ticket) {
        this.ticket = ticket;
        this.eventTime = LocalDateTime.now();
        this.threadString = Thread.currentThread().getId() + ":" + Thread.currentThread().getName();
    }

    @Override
    public long getIdReference() {
        return ticket.getId();
    }

    @Override
    public String getEventName() {
        return "Create-Ticket";
    }

    @Override
    public LocalDateTime getEventTime() {
        return eventTime;
    }

    @Override
    public String getThreadString() {
        return threadString;
    }

}
