import IPC.TCPClient;
import IPC.TCPHost;
import controllers.TicketShop;

import java.io.IOException;

//Class definition
public class Main {

    //Main function, start of program
    public static void main(String[] args) {


        TCPHost server = new TCPHost(12345, new TicketShop());
        server.start();
        TCPClient client = new TCPClient("localhost",12345);

        client.startInteractive();

        String answer = "";
        String answer2 = "";
        try {
            answer = client.send("event;create;Name1,Location1,2030-12-12T09:09:09,1000");
            answer2 = client.send("event;getall");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Answer: " + answer + " alle evnets: " + answer2);

        client.close();
        server.shutdown();
                /*
        PerformanceClient performanceClient = new PerformanceClient();
        performanceClient.run();
        */

        System.exit(0);

    }
}
