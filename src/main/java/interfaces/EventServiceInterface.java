package interfaces;

import models.Event;

import java.time.LocalDateTime;

public interface EventServiceInterface {
    Event createEvent(String name, String location, LocalDateTime time, int ticketsAvailable) throws IllegalArgumentException;
    Event getEventById(long id);
    void updateEvent(Event event) throws IllegalArgumentException;
    void deleteEvent(long id) throws IllegalArgumentException;
    Iterable<Event> getAllEvents();
    void deleteAllEvents();
}
