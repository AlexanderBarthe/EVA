package controllers;

import com.sun.jdi.InternalException;
import models.Customer;
import models.Event;
import models.Ticket;
import services.CustomerService;
import services.EventService;
import services.TicketService;

public class TicketController {
    
    private TicketService ticketService;
    private CustomerService customerService;
    private EventService eventService;
    
    public TicketController() {
        this.ticketService = new TicketService();
        this.customerService = new CustomerService();
        this.eventService = new EventService();
    }

    public void executeString(String input) {

        String[] args = input.split(" ");

        if(args.length == 0) {
            System.out.println("Invalid command");
            return;
        }

        if(args[0].equals("create")) {

            if(args.length < 3) {
                System.out.println("Please specify: Customer Id, Event Id");
                return;
            }

            try {
                Customer customer = customerService.getCustomerById(Long.parseLong(args[1]));
                Event event = eventService.getEventById(Long.parseLong(args[2]));

                if(customer == null) {
                    System.out.println("Customer not found");
                    return;
                }
                if(event == null) {
                    System.out.println("Event not found");
                    return;
                }

                ticketService.createTicket(customer, event);

            } catch (NumberFormatException e) {
                System.err.println("Invalid number");
            } catch (IllegalArgumentException | InternalException e) {
                System.out.println(e.getMessage());
            }

        }
        else if(args[0].equals("get")) {

            if(args.length >= 2) {
                try {
                    long id = Long.parseLong(args[1]);
                    Ticket ticket = ticketService.getTicketById(id);
                    System.out.println(ticket);
                }
                catch(NumberFormatException nfe) {
                    System.err.println("Please give a valid id.");
                } catch (IllegalArgumentException iae) {
                    System.err.println(iae.getMessage());
                }
            }
            else {
                System.err.println("Please give a valid id.");
            }

        }
        else if(args[0].equals("verify")) {
            if(args.length < 4) {
                System.out.println("Please specify: Customer Id, Event Id");
                return;
            }
            try {
                long ticketId = Long.parseLong(args[1]);
                long customerId = Long.parseLong(args[2]);
                long eventId = Long.parseLong(args[3]);
                boolean valid = ticketService.verifyTicket(ticketId, customerId, eventId);
            } catch (NumberFormatException e) {
                System.err.println("Please give a valid id.");
            }
        }
        else if(args[0].equals("delete")) {

            if(args.length >= 2) {
                try {
                    long id = Long.parseLong(args[1]);
                    ticketService.deleteTicket(id);
                    System.out.println("Ticket deleted.");
                }
                catch(NumberFormatException nfe) {
                    System.err.println("Please give a valid id.");
                } catch (IllegalArgumentException iae) {
                    System.err.println(iae.getMessage());
                }
            }
            else {
                System.err.println("Please give a valid id.");
            }

        }

        else if(args[0].equals("listAll")) {

            ticketService.getAllTickets().forEach(System.out::println);

        }
        else if(args[0].equals("deleteAll")) {

            ticketService.deleteAllTickets();
            System.out.println("Deleted all the tickets");

        }
        else {
            System.err.println("Invalid command");
        }

    }
    
    
}
