package IPC.remote_services;

import IPC.TCPClient;
import interfaces.EventServiceInterface;
import models.Event;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RemoteEventService implements EventServiceInterface {

    private final TCPClient client;

    public RemoteEventService(TCPClient client) {
        this.client = client;
    }


    @Override
    public Event createEvent(String name, String location, LocalDateTime time, int ticketsAvailable) throws IllegalArgumentException {
        String transport = "event;create;" + name + "," + location +  "," + time + "," + ticketsAvailable;
        try {
            String response = client.send(transport);
            Event event = Event.fromString(response);
            return event;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Event getEventById(long id) {
        return null;
    }

    @Override
    public void updateEvent(Event event) throws IllegalArgumentException {

    }

    @Override
    public void deleteEvent(long id) throws IllegalArgumentException {

    }

    @Override
    public List<Event> getAllEvents() {
        try {
            String response = client.send("event;getall;");
            String[] eventStrings = response.split(";");
            List<Event> events = new ArrayList<>();
            for (String eventString : eventStrings) {
                events.add(Event.fromString(eventString));
            }
            return events;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAllEvents() {

    }
}
