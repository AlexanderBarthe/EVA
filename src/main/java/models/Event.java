package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Event {

    private long id;
    private String name;
    private String location;
    private LocalDateTime time;
    private AtomicInteger ticketsAvailable;
    private List<Ticket> soldtickets = new LinkedList<>();

    public Event(long id, String name, String location, LocalDateTime time, int ticketsAvailable) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.time = time;
        this.ticketsAvailable = new AtomicInteger(ticketsAvailable);
    }

    public Event(Event event) {
        this.id = event.id;
        this.name = event.name;
        this.location = event.location;
        this.time = event.time;
        this.ticketsAvailable = event.ticketsAvailable;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public AtomicInteger getTicketsAvailable() {
        return ticketsAvailable;
    }

    public void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable = new AtomicInteger(ticketsAvailable);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTicket(Ticket ticket) {soldtickets.add(ticket);}

    public String toString() {
        return "Event [id=" + id + ",name=" + name + ", location=" + location + ", time=" + time + ", ticketsAvailable=" + ticketsAvailable + "]";
    }
}
