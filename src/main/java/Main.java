import java.time.LocalDateTime;

//Class definition
public class Main {

    //Main function, start of program
    public static void main(String[] args) {

        Event konzert = new Event("Electic Callboy", "Leipzig", LocalDateTime.now().plusHours(6), 10000);
        System.out.println(konzert);
        System.out.println("Übrige Tickets: " + konzert.getTicketsAvailable());

        Event pairProgramming = new Event("Pair Programm Verteilte Anwendungen", "Universität Leipzig I125", LocalDateTime.now(), 13);
        System.out.println("Raum für Pair Programming: " + pairProgramming.getLocation());

    }
}
