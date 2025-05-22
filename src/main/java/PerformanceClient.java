import controllers.TicketShop;
import interfaces.CustomerServiceInterface;
import interfaces.EventServiceInterface;
import interfaces.TicketServiceInterface;
import logging.LogService;
import models.Customer;
import models.Event;
import models.Ticket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class PerformanceClient {

    private final TicketShop ticketShop;

    private final LogService logService;

    private final EventServiceInterface eventService;
    private final CustomerServiceInterface customerService;
    private final TicketServiceInterface ticketService;



    private final ExecutorService executor;

    public PerformanceClient() {
        this.ticketShop = new TicketShop();
        this.logService = ticketShop.getLogService();
        this.eventService = ticketShop.getEventService();
        this.customerService = ticketShop.getCustomerService();
        this.ticketService = ticketShop.getTicketService();
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void run() {
        System.out.println("Starting performance test...");
        long startTime = System.currentTimeMillis();

        //test();
        try {
            testParallel();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Total time: " + (endTime - startTime) + " ms");
        System.out.println("Available tickets for event 1: " + eventService.getAllEvents().get(0).getTicketsAvailable());
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


    private void testParallel() throws InterruptedException {

        // --- Schritt 1 + 2 parallel starten ---
        CountDownLatch latchStep1 = new CountDownLatch(100);
        CountDownLatch latchStep2 = new CountDownLatch(1000);

        long startTime1 = System.currentTimeMillis();

        // Schritt 1: 100 Events erzeugen
        for (int i = 1; i <= 100; i++) {
            final int idx = i;
            executor.submit(() -> {
                try {
                    eventService.createEvent(
                            "Event " + idx,
                            "Location " + idx,
                            LocalDateTime.now().plusDays(idx),
                            100000
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latchStep1.countDown();
                }
            });
        }

        // Schritt 2: 1000 Customers erzeugen
        for (int i = 1; i <= 1000; i++) {
            final int idx = i;
            executor.submit(() -> {
                customerService.createCustomer(
                        "Customer" + idx,
                        "customer" + idx + "@example.com",
                        LocalDate.of(1994, 10, 1)
                );
                latchStep2.countDown();
            });
        }

        // warte auf beide Gruppen
        latchStep1.await();
        latchStep2.await();

        long duration1 = System.currentTimeMillis() - startTime1;

        System.out.println("Schritt 1+2 fertig: 100 Events und 1000 Customers erstellt. Zeit: " + duration1 + " ms");

        // hole Snapshots
        List<Event>    firstEvents    = eventService.getAllEvents();
        List<Customer> allCustomers   = customerService.getAllCustomers();

        long startTime3 = System.currentTimeMillis();

        // --- Schritt 3: für jeden Customer ein Ticket pro vorhandenem Event ---
        CountDownLatch latchStep3 = new CountDownLatch(firstEvents.size() * allCustomers.size());
        for (Event e : firstEvents) {
            for (Customer c : allCustomers) {
                executor.submit(() -> {
                    try {
                        ticketService.createTicket(c, e);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        latchStep3.countDown();
                    }
                });
            }
        }
        latchStep3.await();

        long duration3 = System.currentTimeMillis() - startTime3;

        System.out.println("Schritt 3 fertig: je 1 Ticket für jeden Customer pro Event. Zeit: " + duration3 + " ms");

        // --- Schritt 4: weitere 100 Events erzeugen ---

        long startTime4 = System.currentTimeMillis();

        CountDownLatch latchStep4 = new CountDownLatch(100);
        for (int i = 101; i <= 200; i++) {
            final int idx = i;
            executor.submit(() -> {
                try {
                    eventService.createEvent(
                            "Event " + idx,
                            "Location " + idx,
                            LocalDateTime.now().plusDays(idx),
                            1000000
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latchStep4.countDown();
                }
            });
        }
        latchStep4.await();

        long duration4 = System.currentTimeMillis() - startTime4;

        System.out.println("Schritt 4 fertig: zusätzliche 100 Events erstellt. Zeit: " + duration4 + " ms");

        // hole die neuen Events allein
        List<Event> newEvents = eventService.getAllEvents()
                .subList(firstEvents.size(), firstEvents.size() + 100);

        // --- Schritt 5: je 2 Tickets pro Customer für jedes neue Event ---

        long startTime5 = System.currentTimeMillis();

        CountDownLatch latchStep5 = new CountDownLatch(newEvents.size() * allCustomers.size() * 2);
        for (Event e : newEvents) {
            for (Customer c : allCustomers) {
                // zwei Tickets
                executor.submit(() -> {
                    try {
                        ticketService.createTicket(c, e);
                        ticketService.createTicket(c, e);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        // countdown zweimal
                        latchStep5.countDown();
                        latchStep5.countDown();
                    }
                });
            }
        }
        latchStep5.await();

        long duration5 = System.currentTimeMillis() - startTime5;

        System.out.println("Schritt 5 fertig: je 2 Tickets pro Customer für jedes neue Event. Zeit: " + duration5 + " ms");

        // alles durch, Executor sauber beenden
        executor.shutdown();
        if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }

    }

    public void endExecutor(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                    System.err.println("Executor did not terminate!");
                }
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
