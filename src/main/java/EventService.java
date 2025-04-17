import com.sun.jdi.InternalException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class EventService {

    private HashMap<Long, Event> eventsById;
    private IDService idService;

    public EventService() {
        this.eventsById = new HashMap<>();
        idService = new IDService();
    }

    public Event createEvent(String name, String location, LocalDateTime time, int ticketsAvailable) throws IllegalArgumentException{

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

    public void updateEvent(Event event) throws IllegalArgumentException {

        if(!eventsById.containsKey(event.getId())) {
            throw new IllegalArgumentException("Event with id " + event.getId() + " does not exist.");
        }

        saveEvent(event);

    }

    public void deleteEvent(long id) throws IllegalArgumentException {

        if(!eventsById.containsKey(id)) {
            throw new IllegalArgumentException("Event with id " + id + " does not exist.");
        }

        idService.removeId(id);
        eventsById.remove(id);
    }


    public Collection<Event> getAllEvents() {
        return eventsById.values();
    }

    public void deleteAllEvents() {
        eventsById.clear();
        idService.dropAllIds();
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
