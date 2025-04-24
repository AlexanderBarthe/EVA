package controllers;

import com.sun.jdi.InternalException;
import jdk.jshell.spi.ExecutionControl;
import models.Customer;
import models.Event;
import models.Ticket;
import services.CustomerService;
import services.EventService;
import services.TicketService;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

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
        else if(args[0].equals("update")) {

            if(args.length < 4) {
                System.out.println("Please specify: Ticket Id, key (name/location/time/tickets, value");
            }

            Ticket ticket = null;

            try {
                ticket = ticketService.getTicketById(Long.parseLong(args[1]));
            } catch (NumberFormatException nfe) {
                System.err.println("Please give a valid id.");
                return;
            } catch (IllegalArgumentException iae) {
                System.err.println(iae.getMessage());
                return;
            }

            if(ticket == null) {
                System.err.println("An unknown error occurred.");
                return;
            }
            try {
                switch (args[2]) {
                    case "name":
                        ticket.setName(args[3]);
                        ticketService.updateTicket(ticket);
                        break;
                    case "location":
                        ticket.setLocation(args[3]);
                        ticketService.updateTicket(ticket);
                        break;
                    case "time":
                        ticket.setTime(LocalDateTime.parse(args[3], formatter));
                        ticketService.updateTicket(ticket);
                        break;
                    case "tickets":
                        ticket.setTicketsAvailable(Integer.parseInt(args[3]));
                        ticketService.updateTicket(ticket);
                        break;
                    default:
                        System.err.println("Invalid key. Please give name, location, time or tickets");
                        return;
                }
            } catch (DateTimeParseException dpe) {
                System.err.println("The date is wrongly formatted. The right format is dd.MM.yyyy,HH:mm");
            } catch (NumberFormatException nfe) {
                System.err.println("The number you gave is invalid");
            } catch (IllegalArgumentException iae) {
                System.err.println(iae.getMessage());
            }

            System.out.println("Ticket updated.");

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
