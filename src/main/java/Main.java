import utility.PrimeNumberGenerator;

import java.time.LocalDateTime;

//Class definition
public class Main {

    //Main function, start of program
    public static void main(String[] args) {

        EventService eventService = new EventService();
        Event event1 = eventService.createEvent("Electic Callboy",
                "Leipzig",
                LocalDateTime.now().plusHours(6),
                10000);
        Event event2 = eventService.createEvent("Pair Programm Verteilte Anwendungen",
                "Universität Leipzig I125",
                LocalDateTime.now().plusMinutes(5),
                13);
        Event event3 = eventService.createEvent("Spieleabend Fsr Informatik",
                "Uni Leipzig, Felix Klein Hörsaal",
                LocalDateTime.of(2025, 4, 24, 19, 00),
                200);

        Event fetchedEvent2 = eventService.getEventById(event2.getId());
        fetchedEvent2.setTime(LocalDateTime.now().plusHours(2));
        fetchedEvent2.setTicketsAvailable(10);
        eventService.updateEvent(fetchedEvent2);

        eventService.deleteEvent(event3.getId());

        Event fetchedEvent1 = eventService.getEventById(event1.getId());

        System.out.println(fetchedEvent1);
        System.out.println(fetchedEvent2);



    }
}
