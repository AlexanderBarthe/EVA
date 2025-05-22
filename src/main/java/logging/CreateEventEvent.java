package logging;

import models.Event;

import java.time.LocalDateTime;

public class CreateEventEvent implements LoggableEvent {

    private Event event;
    private LocalDateTime eventTime;
    private String threadString;

    public CreateEventEvent(Event event) {
        this.event = event;
        this.eventTime = LocalDateTime.now();
        this.threadString = Thread.currentThread().getId() + ":" + Thread.currentThread().getName();
    }


    @Override
    public long getIdReference() {
        return event.getId();
    }

    @Override
    public String getEventName() {
        return "Create-Event";
    }

    @Override
    public LocalDateTime getEventTime() {
        return eventTime;
    }

    @Override
    public String getThreadString() {
        return threadString;
    }
}
