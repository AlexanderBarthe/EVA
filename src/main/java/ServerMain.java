import IPC.TCPHost;
import controllers.Client;
import controllers.TicketShop;
import org.checkerframework.checker.units.qual.C;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args){

        TCPHost server = new TCPHost(12345, new TicketShop());
        server.start();

        Client consoleClient = new Client();
        consoleClient.run();
    }
}
