import java.time.LocalDateTime;

public class Event {

    private String name;
    private String location;
    private LocalDateTime time;
    private int ticketsAvailable;

    public Event(String name, String location, LocalDateTime time, int ticketsAvailable) {
        this.name = name;
        this.location = location;
        this.time = time;
        this.ticketsAvailable = ticketsAvailable;
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
        return "Event [name=" + name + ", location=" + location + ", time=" + time + ", ticketsAvailable=" + ticketsAvailable + "]";
    }
}
