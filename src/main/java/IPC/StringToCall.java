package IPC;

import controllers.TicketShop;
import interfaces.CustomerServiceInterface;
import interfaces.EventServiceInterface;
import interfaces.TicketServiceInterface;
import models.Customer;
import models.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StringToCall {

    private CustomerServiceInterface customerService;
    private EventServiceInterface eventService;
    private TicketServiceInterface ticketService;

    public StringToCall(TicketShop ticketShop) {
        this.customerService = ticketShop.getCustomerService();
        this.eventService = ticketShop.getEventService();
        this.ticketService = ticketShop.getTicketService();
    }

    public String callMethodRemotely(String string) throws Exception {

        String[] args = string.split(";");

        if(args.length < 1) {
            return "Error: Missing Service";
        }
        else if(args.length < 2) {
            return "Error: Missing Method";
        }

        switch (args[0].toLowerCase()) {
            case "customer": return callCustomerMethod(args);
            case "event": return callEventMethod(args);
            case "ticket": return callTicketMethod(args);
            default: throw new IllegalArgumentException("Unknown Service: " + args[0]);

        }

    }

    private String callCustomerMethod(String[] args) throws Exception {

        String[] methodArguments = {};

        if(args.length >= 3) methodArguments = args[2].split(",");

        switch (args[1].toLowerCase()) {

            case "create": {
                if(methodArguments.length < 3) throw new IllegalArgumentException("Missing arguments");
                return customerService.createCustomer(args[0], args[1], LocalDate.parse(args[2])).toString();
            }
            case "getbyid": {
                if(methodArguments.length < 1) throw new IllegalArgumentException("Missing arguments");
                return customerService.getCustomerById(Long.parseLong(methodArguments[0])).toString();
            }
            case "update": {

            }
            case "delete": {
                if(methodArguments.length < 1) throw new IllegalArgumentException("Missing arguments");
                customerService.deleteCustomer(Long.parseLong(methodArguments[0]));
                return "Success";
            }
            case "getall": {
                StringBuilder output = new StringBuilder();
                customerService.getAllCustomers().forEach(c -> output.append(c.toString() + ";"));
                return output.toString();
            }
            case "deleteall": {
                customerService.deleteAllCustomers();
                return "Success";
            }
            default: throw new IllegalArgumentException("Invalid method name");
        }

    }

    private String callEventMethod(String[] args) throws  Exception {
        String [] methodArguments = {};
        if(args.length >= 3) methodArguments = args[2].split(",");

            switch (args[1].toLowerCase()) {
                case "create": {
                    if(methodArguments.length < 3) throw new IllegalArgumentException("Missing arguments");
                    return eventService.createEvent(methodArguments[0], methodArguments[1], LocalDateTime.parse(methodArguments[2]), Integer.parseInt(methodArguments[3])).toString();
                }
                case "getbyid": {
                    if(methodArguments.length < 1) throw new IllegalArgumentException("Missing arguments");
                    return eventService.getEventById(Long.parseLong(methodArguments[0])).toString();
                }
                case "getall": {
                    StringBuilder output = new StringBuilder();
                    eventService.getAllEvents().forEach(e -> output.append(e + ";"));
                    return output.toString();
                }
                case "delete": {
                    if(methodArguments.length < 1) throw new IllegalArgumentException("Missing arguments");
                    eventService.deleteEvent(Long.parseLong(methodArguments[0]));
                    return "Success";
                }
                case "deleteall": {
                    eventService.deleteAllEvents();
                    return "All Events deleted";
                }
                default: throw new IllegalArgumentException("Invalid method name");
            }
        }

    private String callTicketMethod(String[] args) throws Exception {

        String[] methodArguments = {};
        if(args.length >= 3) methodArguments = args[2].split(",");

        switch (args[1].toLowerCase()) {
            case "create": {
                if(methodArguments.length < 2) throw new IllegalArgumentException("Missing arguments");
                long customerId = Long.parseLong(methodArguments[0]);
                Customer customer = customerService.getCustomerById(customerId);

                long eventId = Long.parseLong(methodArguments[1]);
                Event event = eventService.getEventById(eventId);

                return ticketService.createTicket(customer, event).toString();
            }
            case "getbyid": {
                return ticketService.getTicketById(Long.parseLong(methodArguments[0])).toString();
            }
            case "getall": {
                StringBuilder output = new StringBuilder();
                ticketService.getAllTickets().forEach(t -> output.append(t.toString() + ";"));
                return output.toString();
            }
            case "delete": {
                ticketService.deleteTicket(Long.parseLong(methodArguments[0]));
                return "Success";
            }
            case "deleteall": {
                ticketService.deleteAllTickets();
                return "Success";
            }
            case "verify":
                if(methodArguments.length < 3) throw new IllegalArgumentException("Missing arguments");
                return ticketService.verifyTicket(Long.parseLong(methodArguments[0]), Long.parseLong(methodArguments[1]), Long.parseLong(methodArguments[2])) ? "Valid" : "Invalid";
            default: throw new IllegalArgumentException("Invalid method name");
        }

    }


}

