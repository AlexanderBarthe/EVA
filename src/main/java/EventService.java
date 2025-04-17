import java.time.LocalDateTime;
import java.util.HashMap;

public class EventService {

    private HashMap<Long, Event> eventsById;

    public EventService() {
        this.eventsById = new HashMap<>();
    }

    public void createEvent(Event event) {

        if(eventsById.containsKey(event.getId())) {
            throw new IllegalArgumentException("Event with id " + event.getId() + " already exists");
        }

        saveEvent(event);
    }

    public Event getEventById(long id) {
        return eventsById.get(id);
    }

    public void updateEvent(Event event) {

        if(!eventsById.containsKey(event.getId())) {
            throw new IllegalArgumentException("Event with id " + event.getId() + " does not exist.");
        }

        saveEvent(event);

    }

    public void deleteEvent(long id) {

        if(!eventsById.containsKey(id)) {
            throw new IllegalArgumentException("Event with id " + id + " does not exist.");
        }

        eventsById.remove(id);
    }


    public HashMap<Long, Event> getAllEvents() {
        return eventsById;
    }

    private void saveEvent(Event event) {

        if(event.getTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("This date is already in the past.");
        }

        if(event.getTicketsAvailable() < 0) {
            throw new IllegalArgumentException("Amount of tickets cannot be negative.");
        }

        eventsById.put(event.getId(), event);

    }


}
