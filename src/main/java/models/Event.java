package models;

import java.time.LocalDateTime;
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

    public static Event fromString(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Input string is null");
        }
        String prefix = "Event [";
        String suffix = "]";
        if (!string.startsWith(prefix) || !string.endsWith(suffix)) {
            throw new IllegalArgumentException("String does not start with \"" + prefix + "\" or end with \"" + suffix + "\"");
        }
        // Inneren Teil extrahieren: id=...,name=..., location=..., time=..., ticketsAvailable=...
        String content = string.substring(prefix.length(), string.length() - suffix.length());
        try {
            // id
            String keyId = "id=";
            String keyName = ",name=";
            int idxIdStart = content.indexOf(keyId);
            int idxNameKey = content.indexOf(keyName, idxIdStart);
            if (idxIdStart != 0 || idxNameKey < 0) {
                throw new IllegalArgumentException("Cannot find id/name delimiter");
            }
            String idStr = content.substring(idxIdStart + keyId.length(), idxNameKey).trim();

            // name
            String keyLocation = ", location=";
            int idxNameStart = idxNameKey + keyName.length();
            int idxLocationKey = content.indexOf(keyLocation, idxNameStart);
            if (idxLocationKey < 0) {
                throw new IllegalArgumentException("Cannot find name/location delimiter");
            }
            String nameStr = content.substring(idxNameStart, idxLocationKey).trim();

            // location
            String keyTime = ", time=";
            int idxLocationStart = idxLocationKey + keyLocation.length();
            int idxTimeKey = content.indexOf(keyTime, idxLocationStart);
            if (idxTimeKey < 0) {
                throw new IllegalArgumentException("Cannot find location/time delimiter");
            }
            String locationStr = content.substring(idxLocationStart, idxTimeKey).trim();

            // time
            String keyTickets = ", ticketsAvailable=";
            int idxTimeStart = idxTimeKey + keyTime.length();
            int idxTicketsKey = content.indexOf(keyTickets, idxTimeStart);
            if (idxTicketsKey < 0) {
                throw new IllegalArgumentException("Cannot find time/ticketsAvailable delimiter");
            }
            String timeStr = content.substring(idxTimeStart, idxTicketsKey).trim();

            // ticketsAvailable
            int idxTicketsStart = idxTicketsKey + keyTickets.length();
            String ticketsStr = content.substring(idxTicketsStart).trim();

            // Parsen der Werte
            long id = Long.parseLong(idStr);
            String name = nameStr;
            String location = locationStr;
            // LocalDateTime erwartet Format wie "2025-06-12T15:30:00"
            LocalDateTime time = LocalDateTime.parse(timeStr);
            int ticketsAvailable = Integer.parseInt(ticketsStr);

            // Neues Event erstellen
            Event event = new Event(id, name, location, time, ticketsAvailable);
            // soldtickets bleibt leer, da toString sie nicht enthält
            return event;
        } catch (Exception e) {
            throw new IllegalArgumentException("Fehler beim Parsen des Event-Strings: " + e.getMessage(), e);
        }
    }


}
