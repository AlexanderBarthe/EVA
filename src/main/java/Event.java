import java.time.LocalDateTime;

public class Event {

    private long id;
    private String name;
    private String location;
    private LocalDateTime time;
    private int ticketsAvailable;

    public Event(long id, String name, String location, LocalDateTime time, int ticketsAvailable) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.time = time;
        this.ticketsAvailable = ticketsAvailable;
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

    public String toString() {
        return "Event [id=" + id + ",name=" + name + ", location=" + location + ", time=" + time + ", ticketsAvailable=" + ticketsAvailable + "]";
    }
}
