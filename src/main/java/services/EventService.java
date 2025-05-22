package services;

import com.sun.jdi.InternalException;
import interfaces.EventServiceInterface;
import logging.CreateEventEvent;
import logging.LogService;
import models.Event;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EventService implements EventServiceInterface {

    private Map<Long, Event> eventsById = new ConcurrentHashMap<>();
    private final IDService idService;

    private LogService logService;

    public EventService(LogService logService) {
        idService = new IDService();
        this.logService = new LogService();
    }

    @Override
    public Event createEvent(String name, String location, LocalDateTime time, int ticketsAvailable) throws IllegalArgumentException, InternalException{

        long generatedId = idService.generateNewId();

        if(generatedId == -1) {
            throw new InternalException("An error occured while creating a new id");
        }

        Event event = new Event(generatedId, name, location, time, ticketsAvailable);

        if(eventsById.containsKey(event.getId())) {
            throw new IllegalArgumentException("Event with id " + event.getId() + " already exists");
        }

        saveEvent(event);

        logService.log(new CreateEventEvent(event));

        return event;
    }

    public Event getEventById(long id) {
        if(!eventsById.containsKey(id)) {
            return null;
        }
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


    public List<Event> getAllEvents() {
        return new ArrayList<>(eventsById.values());
    }

    public void deleteAllEvents() {
        eventsById.clear();
        idService.dropAllIds();
    }

    private void saveEvent(Event event) throws IllegalArgumentException {

        if(event.getTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("This date is already in the past.");
        }

        if(event.getTicketsAvailable().get() < 0) {
            throw new IllegalArgumentException("Amount of tickets cannot be negative.");
        }

        eventsById.put(event.getId(), event);

    }




}
