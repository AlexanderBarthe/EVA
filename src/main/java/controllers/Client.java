package controllers;

import java.util.Arrays;
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

            String[] args = input.split(" ");

            String[] subCommand = Arrays.copyOfRange(args, 1, args.length);

            switch (args[0]) {
                case "event": eventController.executeString(subCommand); break;
                case "customer": customerController.executeString(subCommand); break;
                case "ticket": ticketController.executeString(subCommand); break;
                case "exit": System.exit(0); break;
                default: System.out.println("Invalid command. Use event, customer, ticket or exit"); break;
            }


        }
    }


}
