import IPC.StringToCall;
import controllers.Client;
import controllers.TicketShop;

import java.io.IOException;
import java.util.StringTokenizer;

//Class definition
public class Main {

    //Main function, start of program
    public static void main(String[] args) {

        //Client client = new Client();
        //client.run();

        TicketShop ticketShop = new TicketShop();
        StringToCall ipc = new StringToCall(ticketShop);
        try {
            ipc.callMethodRemotely("event;create;valorant,berlin,2030-12-12T17:00:00,1000");
            System.out.println(ipc.callMethodRemotely("event;getall"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

                /*
        PerformanceClient performanceClient = new PerformanceClient();
        performanceClient.run();
        */

        System.exit(0);

    }
}
