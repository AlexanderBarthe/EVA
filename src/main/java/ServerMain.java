import IPC.TCPHost;
import controllers.Client;
import controllers.TicketShop;
import org.checkerframework.checker.units.qual.C;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args){

        TicketShop ticketShop = new TicketShop();

        TCPHost server = new TCPHost(12345, ticketShop);
        server.start();

        Client consoleClient = new Client(ticketShop);
        consoleClient.run();
    }
}
