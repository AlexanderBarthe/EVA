import java.time.LocalDateTime;

//Class definition
public class Main {

    //Main function, start of program
    public static void main(String[] args) {

        Event konzert = new Event(1,
                "Electic Callboy",
                "Leipzig",
                LocalDateTime.now().plusHours(6),
                10000);

        Event pairProgramming = new Event(2,
                "Pair Programm Verteilte Anwendungen",
                "Universität Leipzig I125",
                LocalDateTime.now().plusMinutes(5),
                13);

        Event fsrSpieleabend = new Event(3,

                "Spieleabend Fsr Informatik",
                "Uni Leipzig, Felix Klein Hörsaal",
                LocalDateTime.of(2025, 4, 24, 19, 00),
                200);

        EventService eventService = new EventService();
        eventService.createEvent(konzert);
        eventService.createEvent(pairProgramming);
        eventService.createEvent(fsrSpieleabend);

        Event fetchedEvent2 = eventService.getEventById(2L);
        fetchedEvent2.setTime(LocalDateTime.now().plusHours(2));
        fetchedEvent2.setTicketsAvailable(10);
        eventService.updateEvent(fetchedEvent2);

        eventService.deleteEvent(3L);

        Event fetchedEvent1 = eventService.getEventById(1L);

        System.out.println(fetchedEvent1);
        System.out.println(fetchedEvent2);


    }
}
