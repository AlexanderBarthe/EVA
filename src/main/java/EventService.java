import com.sun.jdi.InternalException;

import java.time.LocalDateTime;
import java.util.HashMap;

public class EventService {

    private HashMap<Long, Event> eventsById;
    private IDService idService;

    public EventService() {
        this.eventsById = new HashMap<>();
        idService = new IDService();
    }

    public Event createEvent(String name, String location, LocalDateTime time, int ticketsAvailable) {

        long generatedId = idService.generateNewId();

        if(generatedId == -1) {
            throw new InternalException("An error occured while creating a new id");
        }

        Event event = new Event(generatedId, name, location, time, ticketsAvailable);

        if(eventsById.containsKey(event.getId())) {
            throw new IllegalArgumentException("Event with id " + event.getId() + " already exists");
        }

        saveEvent(event);
        return event;
    }

    public Event getEventById(long id) {
        return new Event(eventsById.get(id));
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

        idService.removeId(id);
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
