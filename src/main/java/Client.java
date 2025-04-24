import controllers.CustomerController;
import controllers.EventController;

import java.util.Scanner;

public class Client {

    private CustomerController customerController;
    private EventController eventController;



    public Client() {
        customerController = new CustomerController();
        eventController = new EventController();
    }

    public void run() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            String firstKeyword = input.substring(0, input.indexOf(" "));
            String command = input.substring(input.indexOf(" ") + 1);


            switch (firstKeyword) {
                case "event": eventController.executeString(command); break;
                case "customer": customerController.executeString(command); break;
                default: System.out.println("Invalid command"); break;
            }


        }
    }


}
