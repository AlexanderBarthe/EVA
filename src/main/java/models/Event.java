package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {

    private long id;
    private String name;
    private String location;
    private LocalDateTime time;
    private int ticketsAvailable;
    private List<Ticket> soldtickets;

    public Event(long id, String name, String location, LocalDateTime time, int ticketsAvailable) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.time = time;
        this.ticketsAvailable = ticketsAvailable;
        this.soldtickets = new ArrayList<>();
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

    public int getTicketsAvailable() {
        return ticketsAvailable;
    }

    public void setTicketsAvailable(int ticketsAvailable) {
        this.ticketsAvailable = ticketsAvailable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTicket(Ticket ticket) {soldtickets.add(ticket);}

    public String toString() {
        return "models.Event [id=" + id + ",name=" + name + ", location=" + location + ", time=" + time + ", ticketsAvailable=" + ticketsAvailable + "]";
    }
}
