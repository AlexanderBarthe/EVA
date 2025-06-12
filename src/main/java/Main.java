import IPC.TCPClient;
import IPC.TCPHost;
import controllers.TicketShop;

import java.io.IOException;

//Class definition
public class Main {

    //Main function, start of program
    public static void main(String[] args) {
        TCPClient client = new TCPClient("172.24.166.211",12345);
        client.startInteractive();


        client.close();
        //server.shutdown();


        /*
        PerformanceClient performanceClient = new PerformanceClient();
        performanceClient.run();
        */

        System.exit(0);

    }
}
