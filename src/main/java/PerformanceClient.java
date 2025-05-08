import controllers.EventController;
import controllers.TicketShop;
import interfaces.CustomerServiceInterface;
import interfaces.EventServiceInterface;
import interfaces.TicketServiceInterface;
import models.Customer;
import models.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PerformanceClient {

    private final TicketShop ticketShop;
    private final EventServiceInterface eventService;
    private final CustomerServiceInterface customerService;
    private final TicketServiceInterface ticketService;


    public PerformanceClient() {
        this.ticketShop = new TicketShop();
        this.eventService = ticketShop.getEventService();
        this.customerService = ticketShop.getCustomerService();
        this.ticketService = ticketShop.getTicketService();
    }

    public void run() {

        System.out.println("Starting performance test...");
        long startTime = System.currentTimeMillis();

        test();

        long endTime = System.currentTimeMillis();

        long totalTime = endTime - startTime;

        System.out.println("Total time: " + totalTime + " ms");

    }

    private void test() {

        for(int i = 0; i < 1000; i++) {
            eventService.createEvent("Event " + i, "Location " + i, LocalDateTime.of(2025, 12, 12, 12, 12), 100000);
        }

        for(int i = 0; i < 10000; i++) {
            customerService.createCustomer("Username " + i, "Email@email.com", LocalDate.of(2000, 1, 1));
        }

        for(Customer customer: customerService.getAllCustomers()) {
            for(Event event: eventService.getAllEvents()) {
                ticketService.createTicket(customer, event);
            }
        }

        ArrayList<Event> events = new ArrayList<>();
        for(int i = 0; i < 1000; i++) {
            Event newEvent = eventService.createEvent("Event " + i, "Location " + i, LocalDateTime.of(2025, 12, 12, 12, 12), 100000);
            events.add(newEvent);
        }

        for(Customer customer: customerService.getAllCustomers()) {
            for(Event event: events) {
                ticketService.createTicket(customer, event);
                ticketService.createTicket(customer, event);
            }
        }

    }
}
