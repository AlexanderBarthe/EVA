package controllers;

import interfaces.EventServiceInterface;
import models.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EventController {

    private final TicketShop ticketShop;
    private final EventServiceInterface eventService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy,HH:mm");

    public EventController(TicketShop ticketShop) {
        this.ticketShop = ticketShop;
        this.eventService = ticketShop.getEventService();
    }


    public void executeString(String[] args) {

        if(args.length == 0 || args[1].isEmpty()) {
            System.out.println("Invalid command. Use \"event create/get/update/delete/listAll/deleteAll\" to execute a command.");
            return;
        }

        if(args[0].equals("create")) {

            if(args.length < 5) {
                System.out.println("Please specify: Event Name, Location, Time and Tickets");
                return;
            }

            try {
                Event newEvent = eventService.createEvent(args[1], args[2], LocalDateTime.parse(args[3], formatter), Integer.parseInt(args[4]));
                System.out.println(newEvent);
            } catch (DateTimeParseException dpe) {
                System.err.println("The date is wrongly formatted. The right format is dd.MM.yyyy,HH:mm");
            } catch (NumberFormatException nfe) {
                System.err.println("Please enter a valid ticket number");
            } catch (IllegalArgumentException iae) {
                System.err.println(iae.getMessage());
            }

        }
        else if(args[0].equals("get")) {

            if(args.length >= 2) {
                try {
                    long id = Long.parseLong(args[1]);
                    Event event = eventService.getEventById(id);
                    System.out.println(event);
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
                System.out.println("Please specify: Event Id, key (name/location/time/tickets, value");
            }

            Event event = null;

            try {
                event = eventService.getEventById(Long.parseLong(args[1]));
            } catch (NumberFormatException nfe) {
                System.err.println("Please give a valid id.");
                return;
            } catch (IllegalArgumentException iae) {
                System.err.println(iae.getMessage());
                return;
            }

            if(event == null) {
                System.err.println("An unknown error occurred.");
                return;
            }
            try {
                switch (args[2]) {
                    case "name":
                        event.setName(args[3]);
                        eventService.updateEvent(event);
                        break;
                    case "location":
                        event.setLocation(args[3]);
                        eventService.updateEvent(event);
                        break;
                    case "time":
                        event.setTime(LocalDateTime.parse(args[3], formatter));
                        eventService.updateEvent(event);
                        break;
                    case "tickets":
                        event.setTicketsAvailable(Integer.parseInt(args[3]));
                        eventService.updateEvent(event);
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

            System.out.println("Event updated.");

        }
        else if(args[0].equals("delete")) {

            if(args.length >= 2) {
                try {
                    long id = Long.parseLong(args[1]);
                    eventService.deleteEvent(id);
                    System.out.println("Event deleted.");
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

            eventService.getAllEvents().forEach(System.out::println);

        }
        else if(args[0].equals("deleteAll")) {

            eventService.deleteAllEvents();
            System.out.println("Deleted all the events");

        }
        else {
            System.err.println("Invalid command");
        }

    }


}
