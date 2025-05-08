package controllers;

import java.util.Scanner;

public class Client {

    private final TicketShop ticketShop;
    private final EventController eventController;
    private final CustomerController customerController;
    private final TicketController ticketController;


    public Client() {
        this.ticketShop = new TicketShop();
        eventController = new EventController(ticketShop);
        customerController = new CustomerController(ticketShop);
        ticketController = new TicketController(ticketShop);
    }

    public void run() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            if(!input.contains(" ")) {
                System.out.println("Invalid command or not enough arguments.");
                continue;
            }
            String firstKeyword = input.substring(0, input.indexOf(" "));
            String command = input.substring(input.indexOf(" ") + 1);


            switch (firstKeyword) {
                case "event": eventController.executeString(command); break;
                case "customer": customerController.executeString(command); break;
                case "ticket": ticketController.executeString(command); break;
                case "exit": System.exit(0); break;
                default: System.out.println("Invalid command. Use event, customer, ticket or exit"); break;
            }


        }
    }


}
