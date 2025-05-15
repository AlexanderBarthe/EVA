import controllers.TicketShop;
import interfaces.CustomerServiceInterface;
import interfaces.EventServiceInterface;
import interfaces.TicketServiceInterface;
import models.Customer;
import models.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

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
        System.out.println("Total time: " + (endTime - startTime) + " ms");
    }


    private void test() {

        for(int i = 0; i < 100; i++) {
            eventService.createEvent("Event " + i, "Location " + i, LocalDateTime.of(2025, 12, 12, 12, 12), 100000);
        }

        System.out.println(System.currentTimeMillis());
        System.out.println("events created");

        for(int i = 0; i < 1000; i++) {
            customerService.createCustomer("Username " + i, "Email@email.com", LocalDate.of(2000, 1, 1));
        }

        System.out.println(System.currentTimeMillis());
        System.out.println("customers created");

        for(Customer customer: customerService.getAllCustomers()) {
            for(Event event: eventService.getAllEvents()) {
                ticketService.createTicket(customer, event);
            }
        }

        System.out.println(System.currentTimeMillis());
        System.out.println("100.000 tickets are ready with millions more well on the way");

        ArrayList<Event> events = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            Event newEvent = eventService.createEvent("Event " + i, "Location " + i, LocalDateTime.of(2025, 12, 12, 12, 12), 100000);
            events.add(newEvent);
        }

        System.out.println(System.currentTimeMillis());
        System.out.println("new events created");


        for(Customer customer: customerService.getAllCustomers()) {
            for(Event event: events) {
                ticketService.createTicket(customer, event);
                ticketService.createTicket(customer, event);
            }
        }

        System.out.println(System.currentTimeMillis());
        System.out.println("done");

    }


    private void testParallel() {

        long startTimestamp = System.currentTimeMillis();

        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Verwende " + cores + " Threads im ThreadPool");
        ExecutorService executor;

        // Create events
        executor = Executors.newFixedThreadPool(cores);
        for (int i = 0; i < 100; i++) {
            final int idx = i;
            executor.submit(() -> eventService.createEvent(
                    "Event " + idx,
                    "Location " + idx,
                    LocalDateTime.of(2025, 12, 12, 12, 12),
                    100000));
        }
        executor.shutdown();
        System.out.println("1. Elapsed time: " + (System.currentTimeMillis() - startTimestamp) + " ms");

        //Create customers
        executor = Executors.newFixedThreadPool(cores);
        for (int i = 0; i < 1000; i++) {
            final int idx = i;
            executor.submit(() -> customerService.createCustomer(
                    "Username " + idx,
                    "Email@email.com",
                    LocalDate.of(2000, 1, 1)));
        }
        executor.shutdown();
        System.out.println("2. Elapsed time: " + (System.currentTimeMillis() - startTimestamp) + " ms");

        //Create tickets
        List<Customer> snapshotCustomers = new ArrayList<>();
        for (Customer c : customerService.getAllCustomers()) {
            snapshotCustomers.add(c);
        }
        List<Event> snapshotEvents = new ArrayList<>();
        for (Event e : eventService.getAllEvents()) {
            snapshotEvents.add(e);
        }

        executor = Executors.newFixedThreadPool(cores);
        for (Customer c : snapshotCustomers) {
            executor.submit(() -> {
                for (Event e : snapshotEvents) {
                    ticketService.createTicket(c, e);
                }
            });
        }
        executor.shutdown();
        System.out.println("3. Elapsed time: " + (System.currentTimeMillis() - startTimestamp) + " ms");

        //Create more events
        List<Event> newEvents = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Event ev = eventService.createEvent(
                    "Event " + i,
                    "Location " + i,
                    LocalDateTime.of(2025, 12, 12, 12, 12),
                    100000);
            newEvents.add(ev);
        }
        System.out.println("4. Elapsed time: " + (System.currentTimeMillis() - startTimestamp) + " ms");

        //Buy new tickets twice fe customer
        executor = Executors.newFixedThreadPool(cores);
        for (Customer c : snapshotCustomers) {
            executor.submit(() -> {
                for (Event e : newEvents) {
                    ticketService.createTicket(c, e);
                    ticketService.createTicket(c, e);
                }
            });
        }
        executor.shutdown();
    }
}
